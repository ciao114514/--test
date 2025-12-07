package com.example.hospital.service;

import com.example.hospital.mapper.SubjectMapper;
import com.example.hospital.mapper.DoctorMapper;
import com.example.hospital.mapper.CheckMapper;
import com.example.hospital.mapper.SetmealMapper;
import com.example.hospital.pojo.Subject;
import com.example.hospital.pojo.Doctor;
import com.example.hospital.pojo.Checks;
import com.example.hospital.pojo.Setmeal;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AiChatService {

    private static final String DEEPSEEK_API_KEY = "sk-21a30b6d0ea44c82bec57a7ac5182e27";
    private static final String DEEPSEEK_API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String MODEL = "deepseek-chat";
    // 设置连接和读取超时时间（毫秒）
    private static final int CONNECT_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 30000;

    @Autowired
    private SubjectMapper subjectMapper;
    
    @Autowired
    private DoctorMapper doctorMapper;
    
    @Autowired
    private CheckMapper checkMapper;
    
    @Autowired
    private SetmealMapper setmealMapper;

    private final ObjectMapper objectMapper;

    public AiChatService() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 获取AI回复
     *
     * @param userMessage 用户消息
     * @param history     对话历史
     * @return AI回复内容
     * @throws Exception 请求异常
     */
    public String getAiResponse(String userMessage, List<Map<String, String>> history) throws Exception {
        // 获取科室信息
        List<Subject> subjects = subjectMapper.selectSubjects();
        String departmentsInfo = subjects.stream()
                .map(subject -> subject.getKname())
                .collect(Collectors.joining(", "));

        // 构建系统角色消息
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "你是一个专业的健康体检助手，为用户提供医疗和体检相关的建议。你的知识包括：\n1. 体检注意事项和流程\n2. 常见健康指标解读（血压、血糖、胆固醇等）\n3. 健康饮食和营养指导\n4. 健康生活方式和运动建议\n5. 体检项目和套餐信息\n\n医院科室包括：" + departmentsInfo + "\n\n回答时请遵循以下原则：\n1. 回答要专业、准确、详细\n2. 使用清晰的格式和结构化信息\n3. 如涉及具体医疗建议，请提醒用户以医生诊断为准\n4. 适当使用emoji让回答更生动\n5. 如果问题涉及数据库查询（如体检项目、套餐等），先尝试从数据库获取信息\n6. 如果用户想要挂号，请引导他们使用挂号功能\n7. 在推荐科室时，先检查该科室是否有医生上班");

        // 构建消息列表
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(systemMessage);

        // 添加历史对话（如果有的话）
        if (history != null) {
            messages.addAll(history);
        }

        // 添加当前用户问题
        Map<String, String> currentUserMessage = new HashMap<>();
        currentUserMessage.put("role", "user");
        currentUserMessage.put("content", userMessage);
        messages.add(currentUserMessage);

        // 构造请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", MODEL);
        requestBody.put("messages", messages);
        requestBody.put("stream", false);
        requestBody.put("temperature", 0.7);

        String jsonBody = objectMapper.writeValueAsString(requestBody);

        // 创建HTTP连接
        URL url = new URL(DEEPSEEK_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + DEEPSEEK_API_KEY);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setDoOutput(true);

        // 发送请求体
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // 读取响应
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                
                JsonNode jsonResponse = objectMapper.readTree(response.toString());
                JsonNode choices = jsonResponse.get("choices");
                if (choices != null && choices.size() > 0) {
                    JsonNode message = choices.get(0).get("message");
                    if (message != null) {
                        return message.get("content").asText();
                    }
                }
            }
        } else {
            // 尝试读取错误响应
            StringBuilder errorResponse = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    errorResponse.append(responseLine.trim());
                }
            } catch (Exception e) {
                // 忽略错误流读取异常
            }
            throw new RuntimeException("AI服务调用失败，状态码: " + responseCode + " 错误信息: " + errorResponse.toString());
        }

        throw new RuntimeException("AI服务调用失败，状态码: " + responseCode);
    }
    
    /**
     * 检查特定科室是否有医生上班
     * 
     * @param department 科室名称
     * @return 如果有医生返回true，否则返回false
     */
    public boolean hasDoctorsInDepartment(String department) {
        try {
            // 从本地数据库查询科室信息
            List<Subject> subjects = subjectMapper.selectSubjects();
            boolean departmentExists = subjects.stream()
                    .anyMatch(subject -> subject.getKname().equals(department));
            
            if (!departmentExists) {
                return false;
            }
            
            // 检查该科室是否有医生
            List<Doctor> doctors = doctorMapper.findDoctorBySection(department);
            return doctors != null && !doctors.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 获取所有科室列表
     * 
     * @return 科室名称列表
     */
    public List<String> getAllDepartments() {
        try {
            List<Subject> subjects = subjectMapper.selectSubjects();
            return subjects.stream()
                    .map(Subject::getKname)
                    .distinct()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * 根据科室获取医生列表
     * 
     * @param department 科室名称
     * @return 医生列表
     */
    public List<Doctor> getDoctorsByDepartment(String department) {
        try {
            return doctorMapper.findDoctorBySection(department);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * 获取数据库中的所有信息，供AI学习和记忆
     * 
     * @return 包含科室、医生等信息的综合数据
     */
    public Map<String, Object> getDatabaseInfo() {
        Map<String, Object> databaseInfo = new HashMap<>();
        
        try {
            // 获取所有科室信息
            List<Subject> subjects = subjectMapper.selectSubjects();
            databaseInfo.put("departments", subjects);
            
            // 获取所有医生信息
            List<Doctor> doctors = doctorMapper.selectList(null);
            databaseInfo.put("doctors", doctors);
            
            // 按科室分组的医生信息
            Map<String, List<Doctor>> doctorsByDepartment = new HashMap<>();
            for (Doctor doctor : doctors) {
                String department = doctor.getdSection();
                if (department != null) {
                    if (!doctorsByDepartment.containsKey(department)) {
                        doctorsByDepartment.put(department, new ArrayList<>());
                    }
                    doctorsByDepartment.get(department).add(doctor);
                }
            }
            databaseInfo.put("doctorsByDepartment", doctorsByDepartment);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return databaseInfo;
    }
    
    /**
     * 获取结构化的医院信息，便于AI理解和记忆
     * 
     * @return 结构化的医院信息
     */
    public Map<String, Object> getStructuredHospitalInfo() {
        Map<String, Object> hospitalInfo = new HashMap<>();
        
        try {
            // 获取所有科室信息
            List<Subject> subjects = subjectMapper.selectSubjects();
            
            // 获取所有检查项目信息
            List<Checks> checks = checkMapper.selectList(null);
            
            // 获取所有套餐信息
            List<Setmeal> setmeals = setmealMapper.selectList(null);
            
            // 构建科室-医生映射
            Map<String, Map<String, Object>> departmentInfo = new HashMap<>();
            
            for (Subject subject : subjects) {
                String departmentName = subject.getKname();
                Map<String, Object> deptDetails = new HashMap<>();
                deptDetails.put("id", subject.getKid());
                deptDetails.put("name", subject.getKname());
                deptDetails.put("category", subject.getName());
                
                // 获取该科室的医生
                List<Doctor> doctors = doctorMapper.findDoctorBySection(departmentName);
                deptDetails.put("doctors", doctors);
                
                departmentInfo.put(departmentName, deptDetails);
            }
            
            hospitalInfo.put("departments", departmentInfo);
            hospitalInfo.put("checks", checks);
            hospitalInfo.put("setmeals", setmeals);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return hospitalInfo;
    }
}
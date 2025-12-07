package com.example.hospital.controller;

import com.example.hospital.service.AiChatService;
import com.example.hospital.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai/chat")
public class AiChatController {

    @Autowired
    private AiChatService aiChatService;

    /**
     * 发送消息给AI并获取回复
     *
     * @param request 包含用户消息和对话历史的请求体
     * @return AI回复内容
     */
    @PostMapping("/message")
    public ResponseData sendMessage(@RequestBody Map<String, Object> request) {
        try {
            String userMessage = (String) request.get("message");
            List<Map<String, String>> history = (List<Map<String, String>>) request.get("history");

            String aiResponse = aiChatService.getAiResponse(userMessage, history);

            return ResponseData.success("获取AI回复成功", aiResponse);
        } catch (Exception e) {
            e.printStackTrace(); // 打印错误堆栈，便于调试
            return ResponseData.fail("获取AI回复失败: " + e.getMessage());
        }
    }
    
    /**
     * 检查特定科室是否有医生上班
     *
     * @param department 科室名称
     * @return 是否有医生的检查结果
     */
    @GetMapping("/checkDepartment")
    public ResponseData checkDepartment(@RequestParam String department) {
        try {
            boolean hasDoctors = aiChatService.hasDoctorsInDepartment(department);
            if (hasDoctors) {
                return ResponseData.success("该科室有医生上班", true);
            } else {
                return ResponseData.success("该科室暂无医生", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.fail("检查科室信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取所有科室列表
     *
     * @return 科室列表
     */
    @GetMapping("/departments")
    public ResponseData getDepartments() {
        try {
            List<String> departments = aiChatService.getAllDepartments();
            return ResponseData.success("获取科室列表成功", departments);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.fail("获取科室列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据科室获取医生列表
     *
     * @param department 科室名称
     * @return 医生列表
     */
    @GetMapping("/doctors")
    public ResponseData getDoctorsByDepartment(@RequestParam String department) {
        try {
            List<com.example.hospital.pojo.Doctor> doctors = aiChatService.getDoctorsByDepartment(department);
            return ResponseData.success("获取医生列表成功", doctors);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.fail("获取医生列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取数据库中的所有信息，供AI学习和记忆
     *
     * @return 包含科室、医生等信息的综合数据
     */
    @GetMapping("/databaseInfo")
    public ResponseData getDatabaseInfo() {
        try {
            Map<String, Object> databaseInfo = aiChatService.getDatabaseInfo();
            return ResponseData.success("获取数据库信息成功", databaseInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.fail("获取数据库信息失败: " + e.getMessage());
        }
    }
    
    /**
     * AI辅助挂号功能
     *
     * @param registrationRequest 挂号请求信息
     * @return 挂号结果
     */
    @PostMapping("/register")
    public ResponseData register(@RequestBody Map<String, Object> registrationRequest) {
        try {
            // 获取请求参数
            String department = (String) registrationRequest.get("department");
            Object doctorIdObj = registrationRequest.get("doctorId");
            String date = (String) registrationRequest.get("date");
            String timeSlot = (String) registrationRequest.get("timeSlot");
            
            // 验证科室是否有医生
            if (!aiChatService.hasDoctorsInDepartment(department)) {
                return ResponseData.fail("该科室暂无医生上班");
            }
            
            // 验证医生是否存在
            List<com.example.hospital.pojo.Doctor> doctors = aiChatService.getDoctorsByDepartment(department);
            boolean doctorExists = false;
            
            if (doctorIdObj != null) {
                // 处理doctorId可能是Integer或String的情况
                Integer doctorId;
                if (doctorIdObj instanceof Integer) {
                    doctorId = (Integer) doctorIdObj;
                } else if (doctorIdObj instanceof String) {
                    try {
                        doctorId = Integer.parseInt((String) doctorIdObj);
                    } catch (NumberFormatException e) {
                        return ResponseData.fail("医生ID格式不正确");
                    }
                } else {
                    return ResponseData.fail("医生ID格式不正确");
                }
                
                // 检查医生是否存在
                for (com.example.hospital.pojo.Doctor doctor : doctors) {
                    if (doctor.getdId() != null && doctor.getdId().equals(doctorId)) {
                        doctorExists = true;
                        break;
                    }
                }
            }
            
            if (!doctorExists) {
                return ResponseData.fail("所选医生不存在或不属于该科室");
            }
            
            // 验证日期是否在有效范围内（这里可以添加更多验证逻辑）
            // ...
            
            return ResponseData.success("挂号信息验证通过", registrationRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.fail("挂号验证失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取结构化的医院信息，便于AI理解和记忆
     *
     * @return 结构化的医院信息
     */
    @GetMapping("/structuredInfo")
    public ResponseData getStructuredHospitalInfo() {
        try {
            Map<String, Object> structuredInfo = aiChatService.getStructuredHospitalInfo();
            return ResponseData.success("获取结构化医院信息成功", structuredInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.fail("获取结构化医院信息失败: " + e.getMessage());
        }
    }
}
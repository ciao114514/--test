package com.example.hospital.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.hospital.mapper.OrderMapper;
import com.example.hospital.pojo.*;
import com.example.hospital.service.*;
import com.example.hospital.utils.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("patient")
@Slf4j
public class PatientController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;
    @Resource
    private OrderMapper orderMapper;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealOrderService setmealorderService;

    String typtstr = null;

    @SneakyThrows
    @RequestMapping(value = "intputPatientExcel",  method = RequestMethod.POST)
    public String intputPatientExcel(HttpServletRequest request, HttpServletResponse response, @RequestPart MultipartFile file, int tid){
        System.out.println(tid);


        log.info("处理导入请求");
        if (!file.isEmpty()) {
            // 获取上传文件的原始名称
            String originalFilename = file.getOriginalFilename();

            // 临时文件路径，可以根据实际情况调整，就是linux或者windows对应路径
            String tempPath = "D:\\" + originalFilename;

            // 将上传的Excel文件写入临时文件
            try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempPath))) {
                byte[] bytes = file.getBytes();
                outputStream.write(bytes);
            }

            // 使用临时文件路径进行数据读取
            String fileName = tempPath;

            EasyExcel.read(fileName, WebSalesReportExportVO.class, new PageReadListener<WebSalesReportExportVO>(dataList -> {
                for (WebSalesReportExportVO partner : dataList) {
                    //添加用户信息
                    System.out.println(JSON.toJSONString(partner));
                    Map<String,Object>  ms = addPatient(JSON.toJSONString(partner));
                    typtstr = ms.get("otype").toString();
                    addSetmealOrder(tid,ms);

                    log.info("读取到一条数据{}", JSON.toJSONString(partner));
                }

            })

            ).sheet().doRead();

            // 可选：在操作完成后删除临时文件（如果不打算保留）
            Files.deleteIfExists(Paths.get(fileName));
        } else {
            throw new RuntimeException("上传的文件为空");

        }
        return typtstr;


    }
    //添加用户
    public  Map<String,Object>  addPatient(String strPatient){
       JSONObject JS =  JSON.parseObject(strPatient);

        Patient p = new Patient();
        p.setPId(JS.getInteger("p_id"));
        p.setPName(JS.getString("p_name"));
        p.setPGender(JS.getString("p_gender"));
        p.setPPhone(JS.getString("p_phone"));
        p.setPAge(JS.getInteger("p_age"));
        p.setPCard(JS.getString("p_card"));
        p.setPEmail(JS.getString("p_email"));
        p.setPPassword("3d7dd7b26500bd0595573b651d0080fd");
        p.setPType(JS.getString("otype"));
        p.setPState(1);

        patientService.insertPatient(p);

        Map<String,Object> m = new HashMap<>();
        m.put("pid",JS.getInteger("p_id"));
        m.put("oStart",JS.getString("oStart"));
        m.put("otype",JS.getString("otype"));


        return  m;
    }
    //套餐订单
    public  void  addSetmealOrder(int tid,Map<String,Object> m){
        Setmeal setmeal = setmealService.selectById(tid);
        SetmealOrders order = new  SetmealOrders();
        order.setPId(Integer.valueOf(m.get("pid").toString()));
        order.setOStart(m.get("oStart").toString());
        order.setOState(1);
        order.setOTotalPrice(setmeal.getPrice());
        order.setOPriceState(0);
        order.setTid(tid);
        order.setTname(setmeal.getTname());
        order.setOtype(m.get("otype").toString());
        setmealorderService.addSetmealOrders(order);
    }


    /**
     * 登录数据验证
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData login(@RequestParam(value = "pId") int pId, @RequestParam(value = "pPassword") String pPassword) {
        Patient patient = this.patientService.login(pId, pPassword);
        if (patient != null) {
            Map<String,String> map = new HashMap<>();
            map.put("pName", patient.getPName());
            map.put("pId", String.valueOf(patient.getPId()));
            map.put("pCard", patient.getPCard());
            String token = JwtUtil.getToken(map);
            map.put("token", token);
            //response.setHeader("token", token);
            return ResponseData.success("登录成功", map);
        } else {
            return ResponseData.fail("登录失败，密码或账号错误");
        }
    }
    /**
     * 根据科室查询所有医生信息
     */
    @RequestMapping("findDoctorBySection")
    public ResponseData findDoctorBySection(@RequestParam(value = "dSection") String dSection){
        return ResponseData.success("根据科室查询所有医生信息成功", this.doctorService.findDoctorBySection(dSection));
    }
    /**
     * 增加挂号信息
     */
    @RequestMapping("addOrder")
    @ResponseBody
    public ResponseData addOrder(Orders order, String arId){
        try {
            order.setOtype("P");
            if (this.orderService.addOrder(order, arId))
                return ResponseData.success("插入挂号信息成功");
            return ResponseData.fail("插入挂号信息失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.fail("插入挂号信息失败: " + e.getMessage());
        }
    }
    /**
     * 根据pId查询挂号
     */
    @RequestMapping("findOrderByPid")
    public ResponseData findOrderByPid(@RequestParam(value = "pId") int pId){
        return ResponseData.success("返回挂号信息成功", this.orderService.findOrderByPid(pId)) ;
    }

    /**
     * 增加学生信息
     */
    @RequestMapping("addPatient")
    @ResponseBody
    public ResponseData addPatient(Patient patient) {
        Boolean bo = this.patientService.addPatient(patient);
        if (bo) {
            return ResponseData.success("注册成功");
        }
        return ResponseData.fail("注册失败！账号或邮箱已被占用");
    }
    @GetMapping("/pdf")
    public void downloadPDF(HttpServletRequest request, HttpServletResponse response, int oId) throws Exception {
        Orders order = this.orderMapper.findOrderByOid(oId);
        PdfUtil.ExportPdf(request, response, order);
    }
    @GetMapping("/pdf2")
    public void downloadPDF2(HttpServletRequest request, HttpServletResponse response, int oId) throws Exception {
        Orders order = this.orderMapper.findOrderByOid(oId);
        PdfUtil2.ExportPdf(request, response, order);
    }

    @GetMapping("/SetmealPdf")
    public void downloadSetmealPDF(HttpServletRequest request, HttpServletResponse response, int oId) throws Exception {

        SetmealOrders order = this.setmealorderService.findOrderByssOid(oId);

        List<Orders> so = this.orderMapper.findOrderBypOid(oId);
        System.out.println(so);
        PdfSetmealUtil3.ExportPdf(request, response, order,so);
    }
    @GetMapping("/SetmealPdf2")
    public void downloadSetmealPDF2(HttpServletRequest request, HttpServletResponse response, int oId) throws Exception {

        SetmealOrders order = this.setmealorderService.findOrderByssOid(oId);

        List<Orders> so = this.orderMapper.findOrderBypOid(oId);
        System.out.println(so);
        PdfSetmealUtil.ExportSetmealPdf(request, response, order,so);
    }


    /**
     * 统计学生男女人数
     */
    @RequestMapping("patientAge")
    public ResponseData patientAge(){
        return  ResponseData.success("统计学生男女人数成功", this.patientService.patientAge());

    }

    @RequestMapping("pdfDownode")
    public void pdfDownode() throws IOException {


        String path = this.getClass().getClassLoader().getResource("").getPath();//注意getResource("")里面是空字符串
        System.out.println(path);
        String filePath = path + "static/仲恺健康体检报告模板.pdf";
        System.out.println(filePath);


        this.getFileContent(filePath);
    }



    /**
     * 需要有明确路径,因为需要输出显示，所以最好是文本文档：“.txt”、“.html”、“.js”等
     * @param fileInPath
     * @throws IOException
     */
    public static void getFileContent(Object fileInPath) throws IOException {
        BufferedReader br = null;
        if (fileInPath == null) {
            return;
        }
        if (fileInPath instanceof String) {
            //如果是字符串路径
            br = new BufferedReader(new FileReader(new File((String) fileInPath)));
        } else if (fileInPath instanceof InputStream) {
            //如果是输入流
            br = new BufferedReader(new InputStreamReader((InputStream) fileInPath));
        }
        String line;
        while ((line = br.readLine()) != null) {
            //System.out.println(line);
        }
        br.close();
    }



}

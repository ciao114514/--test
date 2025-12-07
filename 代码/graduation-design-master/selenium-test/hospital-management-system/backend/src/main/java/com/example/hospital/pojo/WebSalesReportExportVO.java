package com.example.hospital.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class WebSalesReportExportVO {


    @ExcelProperty(value = "账号")
    private Integer p_id;
    @ExcelProperty("姓名")
    private String  p_name;
    @ExcelProperty("性别")
    private String  p_gender;
    @ExcelProperty("年龄")
    private String   p_age;
    @ExcelProperty("证件号")
    private String   p_card;
    @ExcelProperty("手机号")
    private String   p_phone;
    @ExcelProperty("邮箱")
    private String  p_email;
    @ExcelProperty("挂号时间")
    private String  oStart;
    @ExcelProperty("导入状态")
    private String  otype;


}

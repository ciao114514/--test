package com.example.hospital.controller;

import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.example.hospital.pojo.WebSalesReportExportVO;
import lombok.SneakyThrows;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("excel")
public class ExcelController {



    @SneakyThrows
    @GetMapping("/exportReport")
    public void exportReport(HttpServletRequest request, HttpServletResponse response) {
        String fileNameOri = "体检用户信息报表.xlsx";
        // 文件名中文名需要转义
        String fileName = URLEncoder.encode(fileNameOri, "UTF-8");
        String contentType = MediaTypeFactory.getMediaType(fileName).map(MimeType::toString).orElse("application/vnd.ms-excel");
        response.setContentType(contentType);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 这里需要设置不关闭流
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 11);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 10);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        //填充数据
        List<WebSalesReportExportVO> webSalesReportExportVOS = new ArrayList<>();
        WebSalesReportExportVO wx  = new WebSalesReportExportVO();
        wx.setP_id(123456);
        wx.setP_name("李四");
        wx.setP_gender("男");
        wx.setP_age("18");
        wx.setP_card("551222200201013333");
        wx.setP_phone("15977777777");
        wx.setP_email("789@qq.com");
        wx.setOStart("2025-02-15 14:30-17:30");

        wx.setOtype("T");
        webSalesReportExportVOS.add(wx);
        EasyExcel.write(response.getOutputStream(), WebSalesReportExportVO.class)
                .autoCloseStream(Boolean.FALSE)
                .excelType(ExcelTypeEnum.XLSX)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())//自动列宽策略
                .sheet("体检用户信息")
                //获取数据填充
                .doWrite(webSalesReportExportVOS);
    }
}

package com.example.hospital.utils;

import com.example.hospital.pojo.Orders;
import com.example.hospital.pojo.SetmealOrders;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// //  List<Orders> osrder
public class PdfSetmealUtil {

    public static void ExportSetmealPdf(HttpServletRequest request, HttpServletResponse response,
                                        SetmealOrders order,
                                        List<Orders> osrder) throws Exception {
        //告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/pdf");
        //下载文件的默认名称
       // response.setHeader("Content-Disposition", "attachment;filename=XXX.pdf");
        //设置中文
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
        //创建一个文档
        Document document = new Document(PageSize.A4);

        //创建第一个段落
        Paragraph titleParagraph = new Paragraph();
        //支持中文
        titleParagraph.setFont(new Font(bfChinese, 20, Font.NORMAL));
        //设置居中显示
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        titleParagraph.add("仲恺健康体检报告单");
        //创建第二个段落
        Paragraph tipsParagraph = new Paragraph();
        tipsParagraph.setFont(new Font(bfChinese, 10, Font.NORMAL));
        tipsParagraph.setAlignment(Element.ALIGN_CENTER);
        tipsParagraph.setLeading(tipsParagraph.getTotalLeading()+10);
        tipsParagraph.add("打印时间：" + TodayUtil.getTodayYmd());

        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        // 打开文档
        document.open();
        //设置文档标题
        document.addTitle("仲恺健康体检中心");
        //设置文档作者
        document.addAuthor("仲恺健康体检中心");
        document.addCreationDate();
        //设置关键字
        document.addKeywords("iText");
        document.addLanguage("中文");
        //增加段落进入文档
        document.add(titleParagraph);
        document.add(tipsParagraph);
        //表格
        PdfPTable tableMessage = new PdfPTable(4);
        tableMessage.setSpacingBefore(8f);
        tableMessage.setSpacingAfter(8f);
        //设置表格无边框
        tableMessage.getDefaultCell().setBorder(0);
        //设置表格宽度
        tableMessage.setTotalWidth(new float[] { 30, 120, 30, 120 });
        tableMessage.addCell(new Paragraph("姓名", FontChinese));
        tableMessage.addCell(new Paragraph(order.getPatient().getPName(), FontChinese));
        tableMessage.addCell(new Paragraph("性别", FontChinese));
        tableMessage.addCell(new Paragraph(order.getPatient().getPGender(), FontChinese));
        tableMessage.addCell(new Paragraph("年龄", FontChinese));
        tableMessage.addCell(new Paragraph(order.getPatient().getPAge() +" 岁", FontChinese));
        tableMessage.addCell(new Paragraph("单号", FontChinese));
        tableMessage.addCell(String.valueOf(order.getOId()));
        tableMessage.addCell(new Paragraph("日期", FontChinese));
        tableMessage.addCell(order.getOStart().substring(0,10));
        tableMessage.addCell(new Paragraph("电话", FontChinese));
        tableMessage.addCell(order.getPatient().getPPhone());
        document.add(tableMessage);


        for (int i =0; i<osrder.size(); i++){
        //病情表格
        PdfPTable tableOrder = new PdfPTable(1);
        //设置表格无边框
        tableOrder.getDefaultCell().setBorder(0);
        tableOrder.setSpacingBefore(30f);
        tableOrder.setSpacingAfter(10f);



             PdfPCell cell0 = new PdfPCell(new Paragraph((i+1)+".检查科室："+osrder.get(i).getDoctor().getdSection()+"             检查医生："+osrder.get(i).getDoctor().getdName(), new Font(bfChinese, 14, Font.NORMAL)));
             cell0.setFixedHeight(25);
             cell0.setBorder(1);
             cell0.setBorderColor(BaseColor.BLACK);
             tableOrder.addCell(cell0);
             PdfPCell cell1 = new PdfPCell(new Paragraph("检查结果", new Font(bfChinese, 14, Font.NORMAL)));
             cell1.setFixedHeight(25);
             cell1.setBorder(0);
             tableOrder.addCell(cell1);

             if(osrder.get(i).getChid()==1111){
                 PdfPTable tablem = new PdfPTable(4);
                 tablem.setSpacingBefore(8f);
                 tablem.setSpacingAfter(8f);
                 //设置表格无边框
                 tablem.getDefaultCell().setBorder(1);
                 tablem.getDefaultCell().setBorderColor(BaseColor.BLACK);

                 //设置表格宽度
                 tablem.setTotalWidth(new float[] { 80, 120, 80, 120 });
                 PdfPCell mergedCell0 = new PdfPCell(new Phrase("身高(m)：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell0.setFixedHeight(28);
                 //mergedCell0.setRowspan(2);
                 mergedCell0.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell0.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell0);

                 PdfPCell mergedCell2 = new PdfPCell(new Phrase( osrder.get(i).getSg(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell2.setFixedHeight(28);
                 mergedCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell2);

                 PdfPCell mergedCell1 = new PdfPCell(new Phrase("体重(kg)：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell1.setFixedHeight(28);
                 //mergedCell1.setRowspan(2);
                 mergedCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell1);

                 PdfPCell mergedCell4 = new PdfPCell(new Phrase(osrder.get(i).getTz(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell4.setFixedHeight(28);
                 mergedCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell4);


                 PdfPCell mergedCell5 = new PdfPCell(new Phrase("血压(kpa)：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell5.setFixedHeight(28);
                 mergedCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell5);

                 PdfPCell mergedCell6 = new PdfPCell(new Phrase(osrder.get(i).getXy(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell6.setFixedHeight(28);
                 mergedCell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell6);

                 PdfPCell mergedCell7 = new PdfPCell(new Phrase("心率(bmp)：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell7.setFixedHeight(28);
                 mergedCell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell7);

                 PdfPCell mergedCell8 = new PdfPCell(new Phrase(osrder.get(i).getXl(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell8.setFixedHeight(28);
                 mergedCell8.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell8);

                 PdfPCell cell2 = new PdfPCell(tablem);
                 cell2.setFixedHeight(100);
                 cell2.setBorder(0);
                 cell2.setPaddingLeft(5);
                 tableOrder.addCell(cell2);


             } else if(osrder.get(i).getChid()==2222){
                 PdfPTable tablem = new PdfPTable(4);
                 tablem.setSpacingBefore(8f);
                 tablem.setSpacingAfter(8f);
                 //设置表格无边框
                 tablem.getDefaultCell().setBorder(1);
                 tablem.getDefaultCell().setBorderColor(BaseColor.BLACK);

                 //设置表格宽度
                 tablem.setTotalWidth(new float[] { 80, 120, 80, 120 });
                 PdfPCell mergedCell0 = new PdfPCell(new Phrase("听力：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell0.setFixedHeight(28);
                 //mergedCell0.setRowspan(2);
                 mergedCell0.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell0.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell0);

                 PdfPCell mergedCell1 = new PdfPCell(new Phrase(osrder.get(i).getTl(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell1.setColspan(3);
                 mergedCell1.setFixedHeight(28);
                 mergedCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell1);

                 PdfPCell mergedCell3 = new PdfPCell(new Phrase("左耳(m)：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell3.setFixedHeight(28);
                 mergedCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
                 mergedCell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell3);

                 PdfPCell mergedCell4 = new PdfPCell(new Phrase(osrder.get(i).getZr(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell4.setFixedHeight(28);
                 mergedCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell4);

                 PdfPCell mergedCell5 = new PdfPCell(new Phrase("右耳(m)：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell5.setFixedHeight(28);
                 mergedCell5.setHorizontalAlignment(Element.ALIGN_LEFT);
                 mergedCell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell5);

                 PdfPCell mergedCell6 = new PdfPCell(new Phrase(osrder.get(i).getYr(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell6.setFixedHeight(28);
                 mergedCell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell6);

                 PdfPCell mergedCell7 = new PdfPCell(new Phrase("嗅觉：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell7.setFixedHeight(28);
                 mergedCell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell7);

                 PdfPCell mergedCell8 = new PdfPCell(new Phrase(osrder.get(i).getXj(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell8.setFixedHeight(28);
                 mergedCell8.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell8);

                 PdfPCell mergedCell9 = new PdfPCell(new Phrase("口吃：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell9.setFixedHeight(28);
                 mergedCell9.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell9.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell9);

                 PdfPCell mergedCell10 = new PdfPCell(new Phrase(osrder.get(i).getKc(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell10.setFixedHeight(28);
                 mergedCell10.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell10.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell10);

                 PdfPCell cell2 = new PdfPCell(tablem);
                 cell2.setFixedHeight(100);
                 cell2.setBorder(0);
                 cell2.setPaddingLeft(5);
                 tableOrder.addCell(cell2);

             }else
             //获取眼科
             if(osrder.get(i).getChid()==2223){

                 PdfPTable tablem = new PdfPTable(4);
                 tablem.setSpacingBefore(8f);
                 tablem.setSpacingAfter(8f);
                 //设置表格无边框
                 tablem.getDefaultCell().setBorder(1);
                 tablem.getDefaultCell().setBorderColor(BaseColor.BLACK);

                 //设置表格宽度
                 tablem.setTotalWidth(new float[] { 80, 120, 80, 120 });
                 PdfPCell mergedCell0 = new PdfPCell(new Phrase("裸眼视力", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell0.setFixedHeight(60);
                 mergedCell0.setRowspan(2);
                 mergedCell0.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell0.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell0);

                 PdfPCell mergedCell2 = new PdfPCell(new Phrase("左："+ osrder.get(i).getZy(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell2.setFixedHeight(28);
                 mergedCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell2);

                 PdfPCell mergedCell1 = new PdfPCell(new Phrase("矫正视力", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell1.setFixedHeight(60);
                 mergedCell1.setRowspan(2);
                 mergedCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell1);

                 PdfPCell mergedCell4 = new PdfPCell(new Phrase("左："+ osrder.get(i).getJzy(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell4.setFixedHeight(28);
                 mergedCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell4);


                 PdfPCell mergedCell5 = new PdfPCell(new Phrase("右："+ osrder.get(i).getYy(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell5.setFixedHeight(28);
                 mergedCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell5);

                 PdfPCell mergedCell6 = new PdfPCell(new Phrase("右："+ osrder.get(i).getJyy(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell6.setFixedHeight(28);
                 mergedCell6.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell6);

                 PdfPCell mergedCell7 = new PdfPCell(new Phrase("色觉：" , new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell7.setFixedHeight(28);
                 mergedCell7.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell7);

                 PdfPCell mergedCell8 = new PdfPCell(new Phrase(osrder.get(i).getSj(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell8.setColspan(3);
                 mergedCell8.setFixedHeight(28);
                 mergedCell8.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell8);

                 PdfPCell cell2 = new PdfPCell(tablem);
                 cell2.setFixedHeight(100);
                 cell2.setBorder(0);
                 cell2.setPaddingLeft(5);
                 tableOrder.addCell(cell2);

             }else if(osrder.get(i).getChid()==3333){
                 PdfPTable tablem = new PdfPTable(4);
                 tablem.setSpacingBefore(8f);
                 tablem.setSpacingAfter(8f);
                 //设置表格无边框
                 tablem.getDefaultCell().setBorder(1);
                 tablem.getDefaultCell().setBorderColor(BaseColor.BLACK);

                 tablem.setTotalWidth(new float[] { 80, 120, 80, 120 });
                 PdfPCell mergedCell0 = new PdfPCell(new Phrase("肝功能：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell0.setFixedHeight(28);
                 mergedCell0.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell0.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell0);

                 PdfPCell mergedCell1 = new PdfPCell(new Phrase(osrder.get(i).getGgn(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell1.setFixedHeight(28);
                 mergedCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell1);

                 PdfPCell mergedCell2 = new PdfPCell(new Phrase("乙型肝炎表面抗原：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell2.setFixedHeight(28);
                 mergedCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell2);

                 PdfPCell mergedCell3 = new PdfPCell(new Phrase(osrder.get(i).getYgn(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell3.setFixedHeight(28);
                 mergedCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell3);

                 PdfPCell cell2 = new PdfPCell(tablem);
                 cell2.setFixedHeight(100);
                 cell2.setBorder(0);
                 cell2.setPaddingLeft(5);
                 tableOrder.addCell(cell2);
             }else if(osrder.get(i).getChid()==2224){
                 PdfPTable tablem = new PdfPTable(4);
                 tablem.setSpacingBefore(8f);
                 tablem.setSpacingAfter(8f);
                 //设置表格无边框
                 tablem.getDefaultCell().setBorder(1);
                 tablem.getDefaultCell().setBorderColor(BaseColor.BLACK);

                 tablem.setTotalWidth(new float[] { 80, 120, 80, 120 });
                 PdfPCell mergedCell0 = new PdfPCell(new Phrase("甲状腺：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell0.setFixedHeight(28);
                 mergedCell0.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell0.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell0);

                 PdfPCell mergedCell1 = new PdfPCell(new Phrase(osrder.get(i).getJzx(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell1.setFixedHeight(28);
                 mergedCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell1);

                 PdfPCell mergedCell2 = new PdfPCell(new Phrase("淋巴：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell2.setFixedHeight(28);
                 mergedCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell2);

                 PdfPCell mergedCell3 = new PdfPCell(new Phrase(osrder.get(i).getLb(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell3.setFixedHeight(28);
                 mergedCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell3);

                 PdfPCell cell2 = new PdfPCell(tablem);
                 cell2.setFixedHeight(100);
                 cell2.setBorder(0);
                 cell2.setPaddingLeft(5);
                 tableOrder.addCell(cell2);

             }else if(osrder.get(i).getChid()==7777) {
                 PdfPTable tablem = new PdfPTable(4);
                 tablem.setSpacingBefore(8f);
                 tablem.setSpacingAfter(8f);
                 //设置表格无边框
                 tablem.getDefaultCell().setBorder(1);
                 tablem.getDefaultCell().setBorderColor(BaseColor.BLACK);

                 tablem.setTotalWidth(new float[]{80, 120, 80, 120});
                 PdfPCell mergedCell0 = new PdfPCell(new Phrase("心电图：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell0.setFixedHeight(28);
                 mergedCell0.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell0.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell0);

                 PdfPCell mergedCell1 = new PdfPCell(new Phrase(osrder.get(i).getXdt(), new Font(bfChinese, 14, Font.NORMAL)));

                 mergedCell1.setFixedHeight(28);
                 mergedCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell1);

                 PdfPCell mergedCell3 = new PdfPCell(new Phrase("心律：", new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell3.setFixedHeight(28);
                 mergedCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell3);

                 PdfPCell mergedCell4 = new PdfPCell(new Phrase(osrder.get(i).getXn(), new Font(bfChinese, 14, Font.NORMAL)));
                 mergedCell4.setFixedHeight(28);
                 mergedCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                 mergedCell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                 tablem.addCell(mergedCell4);

                 PdfPCell cell2 = new PdfPCell(tablem);
                 cell2.setFixedHeight(100);
                 cell2.setBorder(0);
                 cell2.setPaddingLeft(5);
                 tableOrder.addCell(cell2);

             }
             else {
                 PdfPCell cell2 = new PdfPCell(new Paragraph("", new Font(bfChinese, 10, Font.NORMAL)));
                 cell2.setFixedHeight(30);
                 cell2.setBorder(0);
                 cell2.setPaddingLeft(10);
                 tableOrder.addCell(cell2);
             }


                PdfPCell cell3 = new PdfPCell(new Paragraph("检查项目及价钱", new Font(bfChinese, 14, Font.NORMAL)));
                cell3.setFixedHeight(25);
                cell3.setBorder(0);
                PdfPCell cell4 = new PdfPCell(new Paragraph(osrder.get(i).getOCheck(), new Font(bfChinese, 10, Font.NORMAL)));
                cell4.setFixedHeight(30);
                cell4.setBorder(0);
                cell4.setPaddingLeft(10);
                PdfPCell cell5 = new PdfPCell(new Paragraph("药物及价钱", new Font(bfChinese, 14, Font.NORMAL)));
                cell5.setFixedHeight(25);
                cell5.setBorder(0);
                PdfPCell cell6 = new PdfPCell(new Paragraph(osrder.get(i).getODrug(), new Font(bfChinese, 10, Font.NORMAL)));
                cell6.setFixedHeight(30);
                cell6.setBorder(0);
                cell6.setPaddingLeft(10);
                PdfPCell cell7 = new PdfPCell(new Paragraph("诊断/医生意见", new Font(bfChinese, 14, Font.NORMAL)));
                cell7.setFixedHeight(25);
                cell7.setBorder(0);
                PdfPCell cell8 = new PdfPCell(new Paragraph(osrder.get(i).getOAdvice(), new Font(bfChinese, 10, Font.NORMAL)));
                cell8.setFixedHeight(100);
                cell8.setBorder(0);
                cell8.setPaddingLeft(10);




                tableOrder.addCell(cell3);
                tableOrder.addCell(cell4);
                tableOrder.addCell(cell5);
                tableOrder.addCell(cell6);
                tableOrder.addCell(cell7);
                tableOrder.addCell(cell8);
                document.add(tableOrder);

                if(i==0){
                    PdfPTable tableSian = new PdfPTable(6);
                    //设置表格无边框
                    tableSian.getDefaultCell().setBorder(0);
                    tableSian.setSpacingBefore(10f);
                    tableSian.setSpacingAfter(300f);

                    tableSian.setTotalWidth(new float[] { 30, 120, 30, 120, 130 , 30 });

                    tableSian.addCell(new Paragraph("", FontChinese));
                    tableSian.addCell(new Paragraph("", FontChinese));
                    tableSian.addCell(new Paragraph("", FontChinese));
                    tableSian.addCell(new Paragraph("", FontChinese));
                    tableSian.addCell(new Paragraph("医生签字：", FontChinese));

                    tableSian.addCell(new Paragraph("", FontChinese));
                    document.add(tableSian);

                }else {
                    PdfPTable tableSian = new PdfPTable(6);
                    //设置表格无边框
                    tableSian.getDefaultCell().setBorder(0);
                    tableSian.setSpacingBefore(10f);
                    tableSian.setSpacingAfter(340f);

                    tableSian.setTotalWidth(new float[] { 30, 120, 30, 120, 130 , 30 });

                    tableSian.addCell(new Paragraph("", FontChinese));
                    tableSian.addCell(new Paragraph("", FontChinese));
                    tableSian.addCell(new Paragraph("", FontChinese));
                    tableSian.addCell(new Paragraph("", FontChinese));
                    tableSian.addCell(new Paragraph("医生签字：", FontChinese));

                    tableSian.addCell(new Paragraph("", FontChinese));
                    document.add(tableSian);

                }





            }





        //增加logo，绝对定位居于右上角
//        Image image = Image.getInstance("src/main/resources/static/images/dgut.jpeg");
//        image.setAbsolutePosition(440,690);
//        document.add(image);

        //设置pdf底部版权说明
        PdfContentByte cb = writer.getDirectContent();
        BaseFont bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
        cb.beginText();
        cb.setFontAndSize(bf, 11);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,  "该报告单仅供参考", 300, 40, 0);
        cb.setFontAndSize(bf,13);
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER,  "版权医院所有", 300, 20, 0);
        cb.endText();





        document.close();
    }
}

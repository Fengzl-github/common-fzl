package com.cn.common.utils;

import jxl.Workbook;
import jxl.format.*;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @Author fengzhilong
 * @Date 2020/10/29 11:07
 * @Desc //TODO 下载到Excel
 **/
public class OutputToExcel {

    //下载到Excel
    public static <T>void downThisPage(String fileName, String titleName, List<T> listData, Class<T> requiredType){

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        HttpServletRequest request = requestAttributes.getRequest();
        //文件名
        fileName = fileName + ".xls";

        try {
            //写到服务器上
            String path = request.getSession().getServletContext().getRealPath("")+"/"+fileName;

            File file = new File(path);

            //创建写工作簿对象
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            //创建工作表
            WritableSheet sheet = workbook.createSheet(titleName,0);
            //设置字体
            WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat cellFormat = new WritableCellFormat(font);

            //设置颜色背景
            cellFormat.setBackground(Colour.WHITE);
            //设置边框
            cellFormat.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
            //设置文字居中对齐方式
            cellFormat.setAlignment(Alignment.CENTRE);
            //设置垂直居中
            cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

            //设置列的宽度
            sheet.getSettings().setDefaultColumnWidth(20);
            //设置自动换行
            cellFormat.setWrap(true);

            //单元格
            Label label0 = new Label(0,0, "工号", cellFormat);
            Label label1 = new Label(1,0, "登录名", cellFormat);
            Label label2 = new Label(2,0, "姓名", cellFormat);
            Label label3 = new Label(3,0, "电话", cellFormat);
            Label label4 = new Label(4,0, "邮箱", cellFormat);
            Label label5 = new Label(5,0, "性别", cellFormat);
            Label label6 = new Label(6,0, "年龄", cellFormat);
            Label label7 = new Label(7,0, "地址", cellFormat);
            sheet.addCell(label0);
            sheet.addCell(label1);
            sheet.addCell(label2);
            sheet.addCell(label3);
            sheet.addCell(label4);
            sheet.addCell(label5);
            sheet.addCell(label6);
            sheet.addCell(label7);

            //给第二行设置背景、样式、字体颜色、对齐方式等
            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
            //设置水平居中对齐
            cellFormat2.setAlignment(Alignment.CENTRE);
            //设置垂直居中
            cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
            cellFormat2.setBackground(Colour.WHITE);
            cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellFormat2.setWrap(true);

            //记录行数
            int n = 1;

            //for (T list: listData){
                Label lt0 = new Label(0, n, "8600", cellFormat2);
                Label lt1 = new Label(1, n, "", cellFormat2);
                Label lt2 = new Label(2, n, "超管", cellFormat2);
                Label lt3 = new Label(3, n, "18233521351", cellFormat2);
                Label lt4 = new Label(4, n, "18233521351@163.com", cellFormat2);
                Label lt5 = new Label(5, n, "男", cellFormat2);
                Label lt6 = new Label(6, n, "23", cellFormat2);
                Label lt7 = new Label(7, n, "北京市通州区马驹桥兴华嘉园7号楼", cellFormat2);

                sheet.addCell(lt0);
                sheet.addCell(lt1);
                sheet.addCell(lt2);
                sheet.addCell(lt3);
                sheet.addCell(lt4);
                sheet.addCell(lt5);
                sheet.addCell(lt6);
                sheet.addCell(lt7);
                n++;
            //}

            //开始执行写入操作
            workbook.write();
            //关闭流
            workbook.close();

        }catch (Exception e){
            e.printStackTrace();
        }


        //下载Excel
        OutputStream outputStream = null;

        try {
            response.addHeader("content-disposition", "attachement;filename="+java.net.URLEncoder.encode(fileName, "utf-8"));

            //下载
            outputStream = response.getOutputStream();
            String path = request.getSession().getServletContext().getRealPath("")+"/"+fileName;

            //
            InputStream inputStream = new FileInputStream(path);
            byte[] b = new byte[4096];
            int size = inputStream.read(b);

            while (size > 0){
                outputStream.write(b, 0, size);
                size = inputStream.read(b);
            }

            outputStream.close();
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


}

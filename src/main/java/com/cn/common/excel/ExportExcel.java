package com.cn.common.excel;

import com.cn.common.excel.param.BaseParam;
import com.cn.common.excel.param.ColumnParam;
import com.cn.common.utils.DateTime;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *@Author fengzhilong
 *@Date 2020/12/18 14:25
 *@Desc 导出工具类
 **/
public class ExportExcel {

    private SXSSFWorkbook workbook;

    private Integer dataLimit = 500000;

    public ExportExcel() {
        //创建一个工作簿
        workbook = new SXSSFWorkbook(5000);
    }

    /**
     * @Author fengzhilong
     * @Desc excel下载
     * @Date 2020/12/18 17:28
     * @param sheetName sheet名称
     * @param baseParam 数据
     * @param out
     * @return void
     **/
    public void exportExcel(String sheetName, BaseParam baseParam, OutputStream out) {

        List data = baseParam.getData();
        int sheetNum = data.size() / dataLimit + 1;
        int startIndex = 0, endIndex = dataLimit;
        for (int i = 0; i < sheetNum; i++) {
            if (endIndex > data.size()) {
                endIndex = data.size();
            }
            createSheet(sheetName + (i + 1), baseParam, startIndex, endIndex);
            startIndex += dataLimit;
            endIndex += dataLimit;
        }

        writeAndDispose(out);
    }


    /**
     * @Author fengzhilong
     * @Desc 创建sheet 大数据量分sheet下载
     * @Date 2020/12/18 17:29
     * @param sheetName sheet名称
     * @param baseParam 数据
     * @param startIndex
     * @param endIndex
     * @return void
     **/
    public void createSheet(String sheetName, BaseParam baseParam, Integer startIndex, Integer endIndex) {
        // 生成一个表格
        Sheet sheet = null;
        synchronized (this) {
            sheet = workbook.createSheet(sheetName);
        }

        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);

        // 声明一个画图的顶级管理器
        Drawing patriarch = sheet.getDrawingPatriarch();

        setExcelHeader(0, baseParam.getColumnParams(), sheet);

        setData(baseParam.getColumnParams(), baseParam.getData().subList(startIndex, endIndex), workbook, sheet, patriarch);
    }


    /**
     * @Author fengzhilong
     * @Desc 填充第一行的标题
     * @Date 2020/12/18 17:31
     * @param index 表示第一行
     * @param columnParams 列描述
     * @param sheet
     * @return void
     **/
    private void setExcelHeader(Integer index, List<ColumnParam> columnParams, Sheet sheet) {
        if (index == null || index < 0) {
            index = 0;
        }
        Row row = sheet.createRow(index);
        for (int i = 0; i < columnParams.size(); i++) {
            ColumnParam columnParam = columnParams.get(i);

            Cell cell = row.createCell(i);
            /*设置第一行样式*/
            CellStyle titleStyle = workbook.createCellStyle();
            /*设置边框*/
            titleStyle.setBorderTop(BorderStyle.MEDIUM);
            titleStyle.setBorderBottom(BorderStyle.MEDIUM);
            titleStyle.setBorderLeft(BorderStyle.MEDIUM);
            titleStyle.setBorderRight(BorderStyle.MEDIUM);
            /*居中*/
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            /*设置字体*/
            Font font = workbook.createFont();
            font.setColor(Font.COLOR_NORMAL);
            font.setBold(true);
            font.setFontHeight((short) 280);

            titleStyle.setFont(font);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(columnParam.getHeaderName());
            sheet.setColumnWidth(i, columnParam.getHeaderName().getBytes().length * 2 * 256);
        }
    }


    /**
     * @Author fengzhilong
     * @Desc 填空数据
     * @Date 2020/12/18 17:32
     * @param columnParams 列描述
     * @param dataset 数据
     * @param workbook
     * @param sheet
     * @param patriarch
     * @return void
     **/
    private void setData(List<ColumnParam> columnParams, Collection dataset, Workbook workbook, Sheet sheet, Drawing patriarch) {
        // 遍历集合数据，产生数据行
        Iterator it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            Row row = sheet.createRow(index);
            Object t = it.next();
            for (int i = 0; i < columnParams.size(); i++) {
                ColumnParam columnParam = columnParams.get(i);
                Cell cell = row.createCell(i);
                /*设置第一行样式*/
                CellStyle dataStyle = workbook.createCellStyle();
                /*设置边框*/
                dataStyle.setBorderBottom(BorderStyle.THIN);
                dataStyle.setBorderTop(BorderStyle.THIN);
                dataStyle.setBorderLeft(BorderStyle.THIN);
                dataStyle.setBorderRight(BorderStyle.THIN);
                /*居中*/
                dataStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
                /*设置字体*/
                Font font = workbook.createFont();
                font.setColor(Font.COLOR_NORMAL);
                font.setFontHeight((short) 250);

                dataStyle.setFont(font);
                cell.setCellStyle(dataStyle);
                try {
                    Object value = PropertyUtils.getProperty(t, columnParam.getFieldName());
                    if (value == null) continue;

                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof byte[]) {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6,
                                index);
                        patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        //判断columnParam的数据处理类是否为null，是的话使用默认的数据处理，否则使用columnParam的
                        if (columnParam.getDataExportConversion() == null) {
                            if (value instanceof Date)
                                textValue = DateTime.date2Str((Date) value);
                            else
                                textValue = value.toString();
                        } else {
                            textValue = columnParam.getDataExportConversion().transferData(value);
                        }

                        //如果textValue为null，跳过
                        if (textValue == null) continue;

                        // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            cell.setCellValue(textValue);
                        }

                        //自适应宽度
                        int columnWidth = sheet.getColumnWidth(i);
                        int newColumnWidth = textValue.getBytes().length * 1 * 256 + 256 * 2;
                        if (newColumnWidth > columnWidth) {
                            sheet.setColumnWidth(i, newColumnWidth);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
    }


    /**
     * @Author fengzhilong
     * @Desc 关闭资源
     * @Date 2020/12/18 17:33
     * @param out
     * @return void
     **/
    public void writeAndDispose(OutputStream out) {
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.dispose();// 删除临时文件
            }
        }
    }


}

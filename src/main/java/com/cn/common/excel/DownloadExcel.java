package com.cn.common.excel;

import com.cn.common.excel.param.BaseParam;
import com.cn.common.utils.DateTime;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @Author fengzhilong
 * @Date 2020/12/20 9:33
 * @Desc 下载excel工具类
 */
public class DownloadExcel {

    /**
     * @Author fengzhilong
     * @Desc excel下载
     * @Date 2020/12/20 9:40
     * @param fileName 文件名
     * @param baseParam 数据
     * @param response
     * @return void
     **/
    public void downLoad(String fileName, BaseParam baseParam, HttpServletResponse response) {

        try {
            OutputStream out = new BufferedOutputStream(response.getOutputStream());

            response.reset();

            String headStr = "attachment; filename=" + URLEncoder.encode(fileName+ DateTime.date2Str(new Date(), "yyyyMMddHHmmss") + ".xlsx", "utf-8");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", headStr);

            ExportExcel exportExcel = new ExportExcel();
            exportExcel.exportExcel(fileName, baseParam, response.getOutputStream());

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
}

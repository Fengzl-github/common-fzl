package com.cn.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Calendar.getInstance;

/**
 * @Author fengzhilong
 * @Date 2020/10/10 10:48
 * @Desc //TODO 日期时间工具类
 **/
public class DateTime extends Date {

    private static final long serialVersionUID = 1L;

    public DateTime(){
        System.currentTimeMillis();
    }

    public DateTime(long date){
        setTime(date);
    }

    public DateTime(int nYear, int nMonth, int nDay, int nHour, int nMin, int nSec){
        Calendar calendar ;
        (calendar = getInstance()).set(nYear, nMonth - 1, nDay, nHour, nMin, nSec);
        long date = calendar.getTimeInMillis();

        setTime(date);
    }

    public String ToString(){
        String pattern = "yyyy-MM-dd HH:mm:ss";

        return (new SimpleDateFormat(pattern)).format(this);
    }

    /**
     * @Author fengzhilong
     * @Desc //TODO 获取特定格式的时间字符串
     * @Date 2020/10/10 13:56
     * @Param [pattern] t/T/Time/Date/DateTime
     * @return java.lang.String
    **/
    public String ToString(String pattern){

        pattern = getMergeFormat(pattern);
        return (new SimpleDateFormat(pattern)).format(this);
    }

    public static DateTime Now(){
        return new DateTime();
    }

   /**
    * @Author fengzhilong
    * @Desc //TODO 格式转换
    * @Date 2020/10/10 14:07
    * @Param [strDateTime, format, formatter]
    * @return java.lang.String
   **/
    public static String Format(String strDateTime, String format, String formatter){
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(strDateTime);
            sdf = new SimpleDateFormat(formatter);
            strDate = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strDate;
    }




    /**
     * @Author fengzhilong
     * @Desc //TODO 特定日期时间格式转换
     * @Date 2020/10/10 13:38
     * @Param [format]
     * @return java.lang.String
    **/
    public String getMergeFormat(String format){

        String formatter = format;
        String regEx = "^(d|f|g|m|r|R|s|U|t|T|Date|Time|DateTime)$";

        Pattern pattern = Pattern.compile(regEx);

        Matcher matcher = pattern.matcher(format);

        if (matcher.find()){
            if (format.equals("t")){
                formatter = "mm:ss";
            } else if (format.equals("T")){
                formatter = "HH:mm:ss";
            } else if (format.equals("Date")){
                formatter = "yyyy-MM-dd";
            } else if (!format.equals("T") && format.equals("Time")){
                if (format.equals("DateTime")){
                    formatter = "yyyy-MM-dd HH:mm:ss";
                }
            }else {
                formatter = "HH:mm:ss";
            }
        }else {
            formatter = format.replaceAll("f", "S");
        }

        return formatter;
    }

}

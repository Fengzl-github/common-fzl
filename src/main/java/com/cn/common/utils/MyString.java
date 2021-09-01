package com.cn.common.utils;


import org.springframework.lang.Nullable;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author fengzhilong
 * @Date 2020/10/10 10:38
 * @Desc //TODO 字符串工具类
 **/
public class MyString {

    /**
     * @Author fengzhilong
     * @Desc //TODO 判断是否为空
     * @Date 2020/10/10 10:43
     * @param strTemp
     * @return boolean
     **/
    public static boolean isEmpty(@Nullable Object strTemp) {
        return ("".equals(strTemp) || strTemp == null);
    }

    /**
     * @Author fengzhilong
     * @Desc //TODO 判断不为空
     * @Date 2020/10/10 10:44
     * @param strTemp
     * @return boolean
     **/
    public static boolean isNotEmpty(String strTemp) {
        return !isEmpty(strTemp);
    }

    /**
     * @Author fengzhilong
     * @Desc //TODO 生成n位随机数
     * @Date 2020/10/10 11:13
     * @param len
     * @return java.lang.String
     **/
    public static String getRandom(int len) {

        int rs = (int) ((Math.random() * 9 + 1) * Math.pow(10, len - 1));

        return String.valueOf(rs);
    }


    /**
     * @Author fengzhilong
     * @Desc 拼接字符串
     * @Date 2020/12/16 17:33
     * @param strFormat
     * @param args
     * @return java.lang.String
     **/
    public static String Format(String strFormat, Object... args) {
        String strReturn = strFormat;

        String pattern = "\\{[0-9]+\\}";
        Pattern c = Pattern.compile(pattern);
        Matcher m = c.matcher(strFormat);
        if (isNotEmpty(strFormat) && m.find()) {

            int x = 0;
            int len = args.length;
            for (int i = 0; i < len; i++) {
                Object arg = args[i];
                if (arg != null) {
                    strReturn = strReturn.replace("{" + x + "}", arg.toString());
                }
                x++;
            }
        }
        return strReturn;
    }


    /**
     * @Author fengzhilong
     * @Desc //TODO 判断手机号是否合法
     * @Date 2020/10/12 13:56
     * @param mobile
     * @return boolean
     **/
    public static boolean isPhoneNumber(String mobile) {
        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobile);

        return matcher.matches();
    }

    /**
     * @Author fengzhilong
     * @Desc //TODO 判断邮箱地址是否合法
     * @Date 2020/12/9 14:09
     * @param email
     * @return boolean
     **/
    public static boolean isEmail(String email) {
        String regex = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /**
     * @Author fengzhilong
     * @Desc //TODO base64编码
     * @Date 2020/12/9 14:09
     * @param pwd
     * @return java.lang.String
     **/
    public static String base64Encode(String pwd) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(pwd.getBytes());

        String strReturn = "";
        try {
            strReturn = new String(encode, "utf-8");
        } catch (Exception e) {
            System.out.println("编码转换错误：" + e.getMessage());
        }
        return strReturn;
    }

    /**
     * @Author fengzhilong
     * @Desc //TODO base64解码
     * @Date 2020/12/9 14:09
     * @param encode
     * @return java.lang.String
     **/
    public static String base64Decode(String encode) {
        System.out.println("encode== " + encode);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(encode.getBytes());
        String strReturn = "";
        try {
            strReturn = new String(decode, "utf-8");
        } catch (Exception e) {
            System.out.println("编码转换错误：" + e.getMessage());
        }
        return strReturn;
    }

    /**
     * @Author fengzhilong
     * @Desc //TODO md5编码16位
     * @Date 2020/12/9 14:10
     * @param pwd
     * @return java.lang.String
     **/
    public static String MD5Encode(String pwd) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(pwd.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }


}

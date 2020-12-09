package com.cn.common.utils;

import com.sun.istack.internal.Nullable;

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
public class myString {

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


    /*public static String Format(String strFormat, Object... args){

        String strReturn = strFormat;
        if (isNotEmpty(strFormat) && strFormat.indexOf(123) >= 0){
            int strFormat1 = 0;
            int len = args.length;

            for(int i = 0; i < len; ++i) {
                Object obj;
                if ((obj = args[i]) != null) {
                    Match match;
                    if ((match = Regex.Match(strReturn = strReturn.replace("{" + strFormat1 + "}", obj.toString()), "(\\{" + strFormat1 + ":0+\\})")).Success) {
                        String var7 = obj.getClass().toString();
                        int var8 = Functions.Substring(match.Value, ":", "}").length();
                        byte var9 = -1;
                        switch(var7.hashCode()) {
                            case -1066470206:
                                if (var7.equals("class java.lang.Integer")) {
                                    var9 = 0;
                                }
                            default:
                                switch(var9) {
                                    case 0:
                                        String var11 = String.format("%0" + var8 + "d", obj);
                                        strReturn = strReturn.replace(match.Value, var11);
                                }
                        }
                    }
                } else {
                    strReturn = strReturn.replace("{" + strFormat1 + "}", "(null)");
                }

                ++strFormat1;
            }
        }
        return strReturn;
    }*/


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

package com.cn.common.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author fengzhilong
 * @Date 2020/9/17 11:37
 * @Desc
 **/
public class ResData implements ResResult {
    private Integer code;
    private String msg;
    private Object data;
    private Integer pushCode;

    public static ResData ok() {
        return new ResData(ResCode.OK);
    }

    public static ResData build(ResCode code) {
        return new ResData(code);
    }

    public static ResData build(ResCode code, String msg) {
        return new ResData(code.getCode(), msg);
    }

    public String getMsg() {
        return msg;
    }

    public ResData setData(Object data) {
        this.data = data;
        return this;
    }

    public ResData putData(String key, Object data) {
        if (this.data == null || !(this.data instanceof Map)) {
            this.data = new HashMap();
        }
        ((Map) this.data).put(key, data);

        return this;
    }


    @Override
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public Object getData() {
        return this.data;
    }

    @Override
    public Object getData(String key) {
        return this.data == null ? null : ((Map) this.data).get(key);
    }

    @Override
    public Object removeData(String key) {
        if (key == null) {
            Object r = this.data;
            this.data = null;
            return r;
        } else {
            return this.data == null ? null : ((Map) this.data).remove(key);
        }
    }

    public ResData(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = this.data;
    }

    public ResData(ResCode resCode) {
        this.code = resCode.getCode();
        this.msg = resCode.getMsg();
    }

    @Override
    public Integer getPushCode() {
        return this.pushCode;
    }

    @Override
    public void setPushCode(Integer pushCode) {
        this.pushCode = pushCode;
    }


}

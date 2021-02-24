package com.cn.common.vo;

/**
 * @Author fengzhilong
 * @Date 2020/9/17 11:55
 * @Desc
 **/
public enum ResCode implements ResResult {
    OK(200, "操作成功"),
    ERROR(-1, "请求失败，请稍后重试"),
    PARAM_VERIFY_FAILED(2001,"参数校验异常");
    private Integer code;
    private String msg;

    private Integer pushCode;

    private ResCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResData setData(Object data) {
        return ResData.ok().setData(data);
    }

    public ResData putData(String key, Object data) {
        return ResData.ok().putData(key, data);
    }

    public ResData msg(String msg) {
        return ResData.build(this, msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
        return null;
    }

    @Override
    public Object getData(String var1) {
        return null;
    }


    @Override
    public Object removeData(String var1) {
        return null;
    }

}

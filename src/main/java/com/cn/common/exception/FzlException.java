package com.cn.common.exception;

import com.cn.common.vo.ResCode;

/**
 * @Author fengzhilong
 * @Date 2020/9/21 11:27
 * @Desc
 **/
public class FzlException extends RuntimeException {

    private Integer code;

    public FzlException(String msg){
        super(msg);
        this.code = -1;
    }
    public FzlException(Integer code, String msg){
        super(msg);
        this.code = code;
    }

    public FzlException(ResCode resCode) {
        super(resCode.getMsg());
        this.code = resCode.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

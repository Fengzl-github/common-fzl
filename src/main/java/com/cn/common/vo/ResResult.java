package com.cn.common.vo;

/**
 * @Author fengzhilong
 * @Date 2020/9/17 11:36
 * @Desc
 **/
public interface ResResult {

    void setCode(Integer code);

    Integer getCode();

    Object getData();

    Object getData(String key);

    Object removeData(String key);


    Integer getPushCode();

    void setPushCode(Integer code);
}

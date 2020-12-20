package com.cn.common.excel.param.conversion;

/**
 * @Author fengzhilong
 * @Date 2020/12/20 9:12
 * @Desc 类型转换接口
 */
public interface DataExportConversion<T> {

    String transferData(T a);
}

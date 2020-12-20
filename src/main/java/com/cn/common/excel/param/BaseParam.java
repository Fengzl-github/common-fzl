package com.cn.common.excel.param;

import lombok.Data;

import java.util.List;

/**
 * @Author fengzhilong
 * @Date 2020/12/20 9:14
 * @Desc
 */
@Data
public class BaseParam<E> {

    private List<E> data;

    private List<ColumnParam> columnParams;

    public List<ColumnParam> setColumnParam(ColumnParam columnParam) {
        this.columnParams.add(columnParam);

        return this.columnParams;
    }

}

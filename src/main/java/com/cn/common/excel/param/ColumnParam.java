package com.cn.common.excel.param;

import com.cn.common.excel.param.conversion.DataExportConversion;
import lombok.Data;

/**
 * @Author fengzhilong
 * @Date 2020/12/20 9:10
 * @Desc
 */
@Data
public class ColumnParam {

    private String headerName;

    private String fieldName;

    private DataExportConversion dataExportConversion;

    public ColumnParam(String headerName, String fieldName) {
        this.headerName = headerName;
        this.fieldName = fieldName;
    }

    public ColumnParam(String headerName, String fieldName, DataExportConversion dataExportConversion) {
        this.headerName = headerName;
        this.fieldName = fieldName;
        this.dataExportConversion = dataExportConversion;
    }
}

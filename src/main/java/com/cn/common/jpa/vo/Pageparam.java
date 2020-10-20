package com.cn.common.jpa.vo;


import lombok.Data;

/**
 * @Author fengzhilong
 * @Date 2020/10/19 15:29
 * @Desc //TODO 分页参数
 **/
@Data
public class Pageparam {
    private Integer pagesize; // 每页条数
    private Integer currentPage; // 当前页数
    private Integer total; // 总条数
}

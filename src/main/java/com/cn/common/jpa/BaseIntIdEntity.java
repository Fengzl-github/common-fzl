package com.cn.common.jpa;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *@Author fengzhilong
 *@Date 2020/12/21 11:22
 *@Desc
 **/
@MappedSuperclass
public class BaseIntIdEntity extends BaseEntity<Integer> {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;

    public BaseIntIdEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

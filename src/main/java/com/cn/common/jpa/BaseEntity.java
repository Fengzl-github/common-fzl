package com.cn.common.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 *@Author fengzhilong
 *@Date 2020/12/21 11:18
 *@Desc
 **/

/**
 * @MappedSuperclass注解只能标注在类上
 *
 * 标注为@MappedSuperclass的类将不是一个完整的实体类。它将不会映射到数据库表，但是它的属性都将映射到其子类的数据库表字段中
 *
 * 标注为@MappedSuperclass的类不能再标注@Entity或@Table注解，也无需实现序列化接口。但是如果一个标注为@MappedSuperclass的类继承了另外一个实体类或者另外一个同样标注了@MappedSuperclass的类的话，他将可以使用@AttributeOverride或@AttributeOverrides注解重定义其父类(无论是否是实体类)的属性映射到数据库表中的字段。
 **/
@MappedSuperclass
public abstract class BaseEntity<ID> {

    @Column(
            insertable = false,
            updatable = false,
            columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP"
    )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BaseEntity() {
    }
}

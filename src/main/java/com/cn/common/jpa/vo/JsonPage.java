package com.cn.common.jpa.vo;


import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @Author fengzhilong
 * @Date 2020/10/19 15:29
 * @Desc //TODO 分页参数
 **/
@Data
public class JsonPage {
    private Integer page = 0; // 当前页数
    private Integer size = 15; // 每页条数

    public JsonPage() {
    }

    public Pageable getPageable() {
        Sort sort = Sort.by(Sort.Direction.DESC, new String[]{"id"});
        return PageRequest.of(this.page, this.size, sort);
    }

    public Pageable getPageableUnsorted() {
        return PageRequest.of(this.page, this.size);
    }

    public Pageable getPageableSorted(Sort sort) {
        return PageRequest.of(this.page, this.size, sort);
    }

    public Pageable getPageable(String oredrStr) {
        Sort sort = Sort.by(Sort.Direction.DESC, new String[]{oredrStr});
        return PageRequest.of(this.page, this.size, sort);
    }
}

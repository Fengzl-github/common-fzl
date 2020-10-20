package com.cn.common.jpa.util;

import com.cn.common.utils.myString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * @Author fengzhilong
 * @Date 2020/10/19 15:28
 * @Desc //TODO 根据分页参数生成pageabel
 **/

public class PageUtil {

    public static Pageable getPageable(Integer currentPage, Integer pagesize, String field){
        Sort sort = null;

        //默认当前页 0
        if (currentPage == null || currentPage < 1){
            currentPage = 0;
        }else {
            currentPage -= 1;
        }

        //默认每页条数 10
        if (pagesize == null || pagesize < 1){
            pagesize = 10;
        }

        //默认采用正序排序
        if (myString.isNotEmpty(field)){
            if (field.contains(" ")){
                String[] split = field.split(" ");
                if (split[1].equals("desc")){
                    sort = Sort.by(Sort.Direction.DESC, split[0]);
                }else {
                    sort = Sort.by(Sort.Direction.ASC, split[0]);
                }
            }else {
                sort = Sort.by(Sort.Direction.ASC, field);
            }
        }

        return PageRequest.of(currentPage, pagesize, sort);
    }
}

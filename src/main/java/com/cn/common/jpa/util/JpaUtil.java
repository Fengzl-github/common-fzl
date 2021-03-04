package com.cn.common.jpa.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author fengzhilong
 * @Date 2020/10/19 16:01
 * @Desc //TODO jpa分页查询
 **/

@Transactional(readOnly = true)
@Repository
public class JpaUtil {

    @PersistenceContext
    private EntityManager em;

    public JpaUtil() {
    }

    public <T> Page<T> page(String hql, Map<String, Object> params, Pageable pageable, Class<T> requiredType){
        System.out.println(hql);
        Query query = em.createQuery(hql);
        if (params != null) {
            Iterator<String> iterator = params.keySet().iterator();

            while(iterator.hasNext()) {
                String key = (String)iterator.next();
                query.setParameter(key, params.get(key));
            }
        }

        if (pageable.isPaged()) {
            query.setFirstResult((int)pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        String cHql = QueryUtils.createCountQueryFor(hql);
        System.out.println("----   " + cHql);
        TypedQuery<Long> cQuery = (TypedQuery)em.createQuery(cHql);
        if (params != null) {
            Iterator iteratorc = params.keySet().iterator();

            while(iteratorc.hasNext()) {
                String key = (String)iteratorc.next();
                cQuery.setParameter(key, params.get(key));
            }
        }

        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> {
            return executeCountQuery(cQuery);
        });
    }


    public <T> List<T>list(String sql, Map<String, Object> params, Class<T> requiredType){

        Query query = this.em.createQuery(sql);
        if (params != null){
            Iterator<String> iterator = params.keySet().iterator();

            while (iterator.hasNext()){
                String key = iterator.next();
                query.setParameter(key, params.get(key));
            }
        }

        return query.getResultList();
    }


    private static Long executeCountQuery(TypedQuery<Long> query) {
        Assert.notNull(query, "TypedQuery must not be null!");
        List<Long> totals = query.getResultList();
        Long total = 0L;

        Long element;
        for(Iterator Iterators = totals.iterator(); Iterators.hasNext(); total = total + (element == null ? 0L : element)) {
            element = (Long)Iterators.next();
        }

        return total;
    }
}


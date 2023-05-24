package com.wlj.firework.core.modular.common.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlj.firework.core.modular.common.model.request.BaseQueryRequest;

import java.util.List;

/**
 * @author wlj
 * @date 2023-05-05
 */
public class PageUtils {

    public static <T> Page<T> createEmptyPage() {
        return createEmptyPage(1L, 10L);
    }

    public static <T> Page<T> createEmptyPage(Long pageNo, Long pageSize) {
        return new Page<>(pageNo, pageSize);
    }

    public static <T> Page<T> createPage(BaseQueryRequest baseQueryRequest) {
        return new Page<>(baseQueryRequest.getPageNo(), baseQueryRequest.getPageSize());
    }

    public static <T> Page<T> createPage(Long pageNo, Long pageSize) {
        return createPage(pageNo, pageSize, 0L, null);
    }

    public static <T> Page<T> createPage(Long pageNo, Long pageSize, Long count) {
        return createPage(pageNo, pageSize, count, null);
    }

    public static <T> Page<T> createPage(Long pageNo, Long pageSize, Long count, List<T> records) {
        Page<T> page = new Page<>(pageNo, pageSize, count);
        page.setRecords(records);
        return page;
    }

}

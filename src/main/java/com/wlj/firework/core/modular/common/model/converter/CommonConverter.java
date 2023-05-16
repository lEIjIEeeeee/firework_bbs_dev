package com.wlj.firework.core.modular.common.model.converter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlj.firework.core.modular.common.model.PageVO;
import com.wlj.firework.core.util.JavaBeanUtils;

/**
 * @author wlj
 * @date 2023-05-05
 */
public class CommonConverter {

    public static <P, T> PageVO<T> convertPage(IPage<P> page, Class<T> responseClass) {
        return PageVO.<T>builder()
                     .pageNo(page.getCurrent())
                     .pageSize(page.getSize())
                     .total(page.getTotal())
                     .dataList(JavaBeanUtils.mapList(page.getRecords(), responseClass))
                     .build();
    }

    public static <T> PageVO<T> convertPage(IPage<T> page) {
        return PageVO.<T>builder()
                     .pageNo(page.getCurrent())
                     .pageSize(page.getSize())
                     .total(page.getTotal())
                     .dataList(page.getRecords())
                     .build();
    }

    public static <P, T> Page<T> convertPageWithConvertFunc(IPage<P> page, ConvertFunc<P, T> convertFunc) {
        Page<T> pageDO = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        return pageDO.setRecords(convertFunc.convertData(page.getRecords()));
    }

}

package com.wlj.firework.core.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wlj
 * @date 2023-05-05
 */
public class JavaBeanUtils {

    public static <T> T map(Object source, Class<T> target, String... ignoreProperties) {
        try {
            T result = target.newInstance();
            copyNonNullProperties(source, result, ignoreProperties);
            return result;
        } catch (Exception e) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "对象转换异常");
        }
    }

    public static void map(Object source, Object target, String... ignoreProperties) {
        copyNonNullProperties(source, target, ignoreProperties);
    }

    public static <T> List<T> mapList(List<?> srcList, Class<T> target) {
        if (CollectionUtil.isEmpty(srcList)) {
            return Collections.emptyList();
        }

        List<T> resultList =CollectionUtil.newArrayList();
        srcList.forEach(src -> resultList.add(map(src, target)));
        return resultList;
    }

    private static void copyNonNullProperties(Object source, Object target, String... ignoreProperties) {
        BeanUtils.copyProperties(source, target, ArrayUtil.addAll(ignoreProperties, getNullPropertyName(source)));
    }

    private static String[] getNullPropertyName(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcPropertyValue = src.getPropertyValue(pd.getName());
            if (srcPropertyValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return ArrayUtil.toArray(emptyNames, String.class);
    }

    //TODO 其余数据类型转换方式

}

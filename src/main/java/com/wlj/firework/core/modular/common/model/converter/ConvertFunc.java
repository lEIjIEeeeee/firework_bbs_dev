package com.wlj.firework.core.modular.common.model.converter;

import java.util.List;

public interface ConvertFunc<S, T> {

    List<T> convertData(List<S> sourceDataList);

}

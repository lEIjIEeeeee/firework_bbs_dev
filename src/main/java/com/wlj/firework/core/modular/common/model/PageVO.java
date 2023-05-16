package com.wlj.firework.core.modular.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageVO<T> {

    @ApiModelProperty(value = "页码")
    private Long pageNo;

    @ApiModelProperty(value = "页大小")
    private Long pageSize;

    @ApiModelProperty(value = "总数据量")
    private Long total;

    @ApiModelProperty(value = "数据列表")
    private List<T> dataList;

}

package com.wlj.firework.core.modular.common.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseQueryRequest {

    @ApiModelProperty(value = "页码")
    @Min(value = 1, groups = {BaseQueryRequest.ListPage.class})
    @NotNull(message = "pageNo不能为空", groups = {BaseQueryRequest.ListPage.class})
    private Long pageNo;

    @ApiModelProperty(value = "页大小")
    @Min(value = 1, groups = {BaseQueryRequest.ListPage.class})
    @Max(value = 100, groups = {BaseQueryRequest.ListPage.class})
    @NotNull(message = "pageSize不能为空", groups = {BaseQueryRequest.ListPage.class})
    private Long pageSize;

    public interface ListPage {
    }

}

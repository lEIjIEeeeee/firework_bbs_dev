package com.wlj.firework.core.modular.auth.model.request;

import com.wlj.firework.core.modular.common.enums.EnableOrDisableStatusEnum;
import com.wlj.firework.core.modular.common.enums.YesOrNoEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuQueryRequest {

    @ApiModelProperty(value = "搜索关键字")
    private String keywords;

    @ApiModelProperty(value = "菜单层级")
    private Integer levels;

    @ApiModelProperty(value = "是否为菜单")
    private YesOrNoEnum menuFlag;

    @ApiModelProperty(value = "状态")
    private EnableOrDisableStatusEnum status;

}

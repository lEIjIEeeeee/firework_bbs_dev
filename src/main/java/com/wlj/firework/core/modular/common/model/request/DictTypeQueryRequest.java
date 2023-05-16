package com.wlj.firework.core.modular.common.model.request;

import com.wlj.firework.core.modular.common.enums.DictTypeBizOrSystemFlagEnum;
import com.wlj.firework.core.modular.common.enums.EnableOrDisableStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DictTypeQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "搜索关键字（code或name）")
    private String keywords;

    @ApiModelProperty(value = "系统或业务标识")
    private DictTypeBizOrSystemFlagEnum type;

    @ApiModelProperty(value = "状态")
    private EnableOrDisableStatusEnum status;

}

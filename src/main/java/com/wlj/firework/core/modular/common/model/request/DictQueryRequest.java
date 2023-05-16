package com.wlj.firework.core.modular.common.model.request;

import com.wlj.firework.core.modular.common.enums.EnableOrDisableStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DictQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "字典类型id")
    @NotBlank(message = "字典类型id不能为空")
    private String dictTypeId;

    @ApiModelProperty(value = "搜索关键字（code或name）")
    private String keywords;

    @ApiModelProperty(value = "状态")
    private EnableOrDisableStatusEnum status;

}

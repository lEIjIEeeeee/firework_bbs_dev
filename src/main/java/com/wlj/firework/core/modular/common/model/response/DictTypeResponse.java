package com.wlj.firework.core.modular.common.model.response;

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
public class DictTypeResponse extends BaseResponse {

    @ApiModelProperty(value = "字典类型编码")
    private String code;

    @ApiModelProperty(value = "字典类型名称")
    private String name;

    @ApiModelProperty(value = "系统或业务标识")
    private String type;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

}

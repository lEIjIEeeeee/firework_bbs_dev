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
public class DictResponse extends BaseResponse {

    @ApiModelProperty(value = "字典类型id")
    private String dictTypeId;

    @ApiModelProperty(value = "字典值编码")
    private String code;

    @ApiModelProperty(value = "字典值名称")
    private String name;

    @ApiModelProperty(value = "扩展字段一（是否默认）")
    private String ext1;

    @ApiModelProperty(value = "扩展字典二")
    private String ext2;

    @ApiModelProperty(value = "扩展字典三")
    private String ext3;

    @ApiModelProperty(value = "扩展字典四")
    private String ext4;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

}

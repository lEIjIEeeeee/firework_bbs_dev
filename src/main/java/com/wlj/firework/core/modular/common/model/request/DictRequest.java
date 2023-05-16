package com.wlj.firework.core.modular.common.model.request;

import com.wlj.firework.core.modular.common.enums.EnableOrDisableStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictRequest {

    @ApiModelProperty(value = "字典值id")
    @NotBlank(message = "id不能为空", groups = {DictRequest.Edit.class})
    private String id;

    @ApiModelProperty(value = "字典类型id")
    @NotBlank(message = "字典类型不能为空")
    private String dictTypeId;

    @ApiModelProperty(value = "字典值编码")
    @NotBlank(message = "编码不能为空")
    private String code;

    @ApiModelProperty(value = "字典值名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private EnableOrDisableStatusEnum status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    public interface Edit {
    }

}

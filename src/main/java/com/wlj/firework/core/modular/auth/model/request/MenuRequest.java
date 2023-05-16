package com.wlj.firework.core.modular.auth.model.request;

import com.wlj.firework.core.modular.common.enums.EnableOrDisableStatusEnum;
import com.wlj.firework.core.modular.common.enums.YesOrNoEnum;
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
public class MenuRequest {

    @ApiModelProperty(value = "菜单id")
    @NotBlank(message = "id不能为空", groups = {MenuRequest.Edit.class})
    private String id;

    @ApiModelProperty(value = "父级id")
    @NotBlank(message = "pid不能为空")
    private String pid;

    @ApiModelProperty(value = "菜单编码")
    @NotBlank(message = "菜单编码不能为空")
    private String code;

    @ApiModelProperty(value = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @ApiModelProperty(value = "菜单url")
    @NotBlank(message = "菜单url不能为空")
    private String url;

    @ApiModelProperty(value = "是否为菜单")
    @NotNull(message = "是否为菜单不能为空")
    private YesOrNoEnum menuFlag;

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

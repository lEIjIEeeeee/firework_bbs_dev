package com.wlj.firework.core.modular.auth.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {

    @ApiModelProperty(value = "角色id")
    @NotBlank(message = "id不能为空", groups = {RoleRequest.Edit.class})
    private String id;

    @ApiModelProperty(value = "父级id")
    @NotBlank(message = "父级id不能为空")
    private String pid;

    @ApiModelProperty(value = "角色编码")
    @NotBlank(message = "角色编码不能为空")
    private String code;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    /**
     * 编辑校验组
     */
    public interface Edit {
    }

}

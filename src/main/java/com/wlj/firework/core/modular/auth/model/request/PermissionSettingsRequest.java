package com.wlj.firework.core.modular.auth.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionSettingsRequest {

    @ApiModelProperty(value = "角色id")
    @NotBlank(message = "角色id不能为空")
    private String roleId;

    @ApiModelProperty(value = "关联菜单id集合")
    private List<String> menuIds;

}

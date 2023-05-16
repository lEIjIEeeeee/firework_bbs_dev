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
public class MenuDeleteRequest {

    @ApiModelProperty(value = "菜单id")
    @NotBlank(message = "id不能为空")
    private String id;

}

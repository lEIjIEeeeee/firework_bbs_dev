package com.wlj.firework.core.modular.common.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseDeleteRequest {

    @ApiModelProperty(value = "主键id")
    @NotBlank(message = "id不能为空")
    private String id;

}

package com.wlj.firework.core.modular.common.model.request;

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
public class DictTypeDeleteRequest {

    @ApiModelProperty(value = "字典类型id")
    @NotBlank(message = "id不能为空")
    private String id;

}

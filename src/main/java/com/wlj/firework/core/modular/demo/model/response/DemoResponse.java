package com.wlj.firework.core.modular.demo.model.response;

import com.wlj.firework.core.modular.common.model.response.BaseResponse;
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
public class DemoResponse extends BaseResponse {

    @ApiModelProperty(value = "名称")
    private String name;

}

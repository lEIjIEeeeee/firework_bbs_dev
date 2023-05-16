package com.wlj.firework.core.modular.demo.model.request;

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
public class DemoRequest {

    @ApiModelProperty(value = "主键id")
    @NotBlank(message = "id不能为空", groups = {DemoRequest.Edit.class})
    private String id;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 修改校验组
     */
    public interface Edit {
    }

}

package com.wlj.firework.core.modular.auth.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDetailResponse {

    @ApiModelProperty(value = "")
    private String id;

    @ApiModelProperty(value = "")
    private String pid;

    @ApiModelProperty(value = "")
    private String code;

    @ApiModelProperty(value = "")
    private String name;

    @ApiModelProperty(value = "")
    private String url;

    @ApiModelProperty(value = "")
    private Integer menuFlag;

    @ApiModelProperty(value = "")
    private Integer systemType;

    @ApiModelProperty(value = "")
    private Integer status;

    @ApiModelProperty(value = "")
    private Integer sort;

    @ApiModelProperty(value = "")
    private String remark;

}

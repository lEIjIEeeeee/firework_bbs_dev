package com.wlj.firework.core.modular.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
    * 字典类型信息表
    */
@ApiModel(description="字典类型信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_dict_type")
public class DictType extends BaseDO {

    /**
     * 字典类型编码
     */
    @TableField(value = "code")
    @ApiModelProperty(value="字典类型编码")
    private String code;

    /**
     * 字典类型名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="字典类型名称")
    private String name;

    /**
     * 字典类型标签 SYSTEM-系统字典 BIZ-业务字典
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value="字典类型标签 SYSTEM-系统字典 BIZ-业务字典")
    private String type;

    /**
     * 状态 0-启用 1-禁用
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 0-启用 1-禁用")
    private Integer status;

    /**
     * 权重
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="权重")
    private Integer sort;

    /**
     * 备注描述
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注描述")
    private String remark;

}
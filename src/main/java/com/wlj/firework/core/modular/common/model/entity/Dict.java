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
    * 字典值信息表
    */
@ApiModel(description="字典值信息表")
@Data

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_dict")
public class Dict extends BaseDO {

    /**
     * 所属字典类型id
     */
    @TableField(value = "dict_type_id")
    @ApiModelProperty(value="所属字典类型id")
    private String dictTypeId;

    /**
     * 字典值编码
     */
    @TableField(value = "code")
    @ApiModelProperty(value="字典值编码")
    private String code;

    /**
     * 字典值名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="字典值名称")
    private String name;

    /**
     * 扩展字段一：是否为默认，可用值：DEFAULT、NON_DEFAULT
     */
    @TableField("ext_1")
    @ApiModelProperty(value = "扩展字段一：是否为默认，可用值：DEFAULT、NON_DEFAULT")
    private String ext1;

    /**
     * 扩展字段二
     */
    @TableField("ext_2")
    @ApiModelProperty(value = "扩展字段二")
    private String ext2;

    /**
     * 扩展字段三
     */
    @TableField("ext_3")
    @ApiModelProperty(value = "扩展字段三")
    private String ext3;

    /**
     * 扩展字段四
     */
    @TableField("ext_4")
    @ApiModelProperty(value = "扩展字段四")
    private String ext4;

    /**
     * 权重
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="权重")
    private Integer sort;

    /**
     * 状态 0-启用 1-禁用
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 0-启用 1-禁用")
    private Integer status;

    /**
     * 备注描述
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注描述")
    private String remark;

}
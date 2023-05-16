package com.wlj.firework.core.modular.auth.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wlj.firework.core.modular.common.model.entity.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
    * 角色信息表
    */
@ApiModel(description="角色信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role")
public class Role extends BaseDO {

    /**
     * 父级id
     */
    @TableField(value = "pid")
    @ApiModelProperty(value="父级id")
    private String pid;

    /**
     * 角色编码
     */
    @TableField(value = "code")
    @ApiModelProperty(value="角色编码")
    private String code;

    /**
     * 角色名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="角色名称")
    private String name;

    /**
     * 权重
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="权重")
    private Integer sort;

    /**
     * 备注信息
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注信息")
    private String remark;

    /**
     * 乐观锁
     */
    @TableField(value = "version")
    @ApiModelProperty(value="乐观锁")
    private Integer version;

}
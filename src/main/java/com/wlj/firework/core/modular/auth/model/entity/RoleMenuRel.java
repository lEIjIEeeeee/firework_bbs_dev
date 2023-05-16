package com.wlj.firework.core.modular.auth.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
    * 角色菜单关联关系表
    */
@ApiModel(description="角色菜单关联关系表")
@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role_menu_rel")
public class RoleMenuRel {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="主键id")
    private String id;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value="角色id")
    private String roleId;

    /**
     * 菜单id
     */
    @TableField(value = "menu_id")
    @ApiModelProperty(value="菜单id")
    private String menuId;

    /**
     * 创建人id
     */
    @TableField(value = "create_id")
    @ApiModelProperty(value="创建人id")
    private String createId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新人id
     */
    @TableField(value = "update_id")
    @ApiModelProperty(value="更新人id")
    private String updateId;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 逻辑删除 0-否 1-是
     */
    @TableField(value = "is_del")
    @ApiModelProperty(value="逻辑删除 0-否 1-是")
    private Integer isDel;
}
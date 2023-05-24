package com.wlj.firework.core.modular.user.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
    * 系统用户表
    */
@ApiModel(description="系统用户表")
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class User {
    /**
     * 主键，用户id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="主键，用户id")
    private String id;

    /**
     * 登录账号
     */
    @TableField(value = "account")
    @ApiModelProperty(value="登录账号")
    private String account;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    @ApiModelProperty(value="密码")
    private String password;

    /**
     * 密码盐
     */
    @TableField(value = "salt")
    @ApiModelProperty(value="密码盐")
    private String salt;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value="角色id")
    private String roleId;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    @ApiModelProperty(value="头像")
    private String avatar;

    /**
     * 用户名
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="用户名")
    private String name;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value="手机号")
    private String phone;

    /**
     * 性别 0-女 1-男
     */
    @TableField(value = "gender")
    @ApiModelProperty(value="性别 0-女 1-男")
    private Integer gender;

    /**
     * 电子邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value="电子邮箱")
    private String email;

    /**
     * 状态 0-正常 1-限制 2-冻结 3-删除
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 0-正常 1-限制 2-冻结 3-删除")
    private Integer status;

    /**
     * 乐观锁
     */
    @TableField(value = "version")
    @ApiModelProperty(value="乐观锁")
    private Integer version;

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
}
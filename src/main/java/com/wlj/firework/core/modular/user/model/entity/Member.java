package com.wlj.firework.core.modular.user.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
    * 用户信息表
    */
@ApiModel(description="用户信息表")
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_member")
public class Member {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="主键id")
    private String id;

    /**
     * 登录账号
     */
    @TableField(value = "account")
    @ApiModelProperty(value="登录账号")
    private String account;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    @ApiModelProperty(value="头像")
    private String avatar;

    /**
     * 用户昵称
     */
    @TableField(value = "nick_name")
    @ApiModelProperty(value="用户昵称")
    private String nickName;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value="手机号")
    private String phone;

    /**
     * 注册类型 0-游客 1-会员
     */
    @TableField(value = "register_type")
    @ApiModelProperty(value="注册类型 0-游客 1-会员")
    private Integer registerType;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    @ApiModelProperty(value="真实姓名")
    private String realName;

    /**
     * 性别 0-女 1-男
     */
    @TableField(value = "gender")
    @ApiModelProperty(value="性别 0-女 1-男")
    private Integer gender;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    @ApiModelProperty(value="生日")
    private Date birthday;

    /**
     * 状态 0-正常 1-限制 2-冻结 3-删除
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 0-正常 1-限制 2-冻结 3-删除")
    private Integer status;

    /**
     * 上一次登录时间
     */
    @TableField(value = "last_login_time")
    @ApiModelProperty(value="上一次登录时间")
    private Date lastLoginTime;

    /**
     * 微信openId
     */
    @TableField(value = "open_id")
    @ApiModelProperty(value="微信openId")
    private String openId;

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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 逻辑删除 0-否 1-是
     */
    @TableField(value = "is_del")
    @TableLogic
    @ApiModelProperty(value="逻辑删除 0-否 1-是")
    private Integer isDel;

}
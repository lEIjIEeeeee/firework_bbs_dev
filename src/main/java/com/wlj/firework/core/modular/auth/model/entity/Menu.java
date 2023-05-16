package com.wlj.firework.core.modular.auth.model.entity;

import cn.hutool.core.lang.tree.Tree;import cn.hutool.core.lang.tree.TreeNodeConfig;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 菜单信息表
 */
@ApiModel(description = "菜单信息表")
@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_menu")
public class Menu {
    /**
     * 主键id，菜单id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键id，菜单id")
    private String id;

    /**
     * 父级id
     */
    @TableField(value = "pid")
    @ApiModelProperty(value = "父级id")
    private String pid;

    /**
     * 父级id集合
     */
    @TableField(value = "pids")
    @ApiModelProperty(value = "父级id集合")
    private String pids;

    /**
     * 菜单编码
     */
    @TableField(value = "code")
    @ApiModelProperty(value = "菜单编码")
    private String code;

    /**
     * 菜单名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "菜单名称")
    private String name;

    /**
     * 菜单url
     */
    @TableField(value = "url")
    @ApiModelProperty(value = "菜单url")
    private String url;

    /**
     * 是否是菜单 0-否 1-是
     */
    @TableField(value = "menu_flag")
    @ApiModelProperty(value = "是否是菜单 0-否 1-是")
    private Integer menuFlag;

    /**
     * 系统分类（字典）
     */
    @TableField(value = "system_type")
    @ApiModelProperty(value = "系统分类（字典）")
    private String systemType;

    /**
     * 状态(字典) 0-启用 1-禁用
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态(字典) 0-启用 1-禁用")
    private Integer status;

    /**
     * 权重
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "权重")
    private Integer sort;

    /**
     * 菜单层级
     */
    @TableField(value = "levels")
    @ApiModelProperty(value = "菜单层级")
    private Integer levels;

    /**
     * 备注信息
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注信息")
    private String remark;

    /**
     * 创建人id
     */
    @TableField(value = "create_id")
    @ApiModelProperty(value = "创建人id")
    private String createId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 更新人id
     */
    @TableField(value = "update_id")
    @ApiModelProperty(value = "更新人id")
    private String updateId;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 逻辑删除 0-否 1-是
     */
    @TableField(value = "is_del")
    @TableLogic
    @ApiModelProperty(value = "逻辑删除 0-否 1-是")
    private Integer isDel;

    public final static String ID = "id";
    public final static String PID = "pid";
    public final static String CODE = "code";
    public final static String NAME = "name";
    public final static String URL = "url";
    public final static String SORT = "sort";

    public final static String TREE_ROOT_ID = "0";
    public final static String TREE_ROOT_PID = "-1";
    public final static String TREE_ROOT_URL = "/";
    public final static String TREE_ROOT_NAME = "菜单树";

    public static Tree<String> buildTreeRoot() {
        return buildTreeRoot(null);
    }

    public static Tree<String> buildTreeRoot(List<Tree<String>> childrenList) {
        Tree<String> root = new Tree<>(getTreeNodeConfig());
        root.setId(TREE_ROOT_ID);
        root.setParentId(TREE_ROOT_PID);
        root.setName(TREE_ROOT_NAME);
        root.setChildren(childrenList);
        root.setWeight(1);
        root.putExtra(CODE, Menu.TREE_ROOT_ID);
        root.putExtra(URL, Menu.TREE_ROOT_URL);
        return root;
    }

    public static TreeNodeConfig getTreeNodeConfig() {
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey(ID);
        treeNodeConfig.setParentIdKey(PID);
        treeNodeConfig.setNameKey(NAME);
        treeNodeConfig.setWeightKey(SORT);
        return treeNodeConfig;
    }
}
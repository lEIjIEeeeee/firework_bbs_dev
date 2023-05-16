package com.wlj.firework.core.modular.common.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseDO {

    /**
     * 主键id，角色id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value="主键id")
    private String id;
    public final static String ID = "id";

    /**
     * 创建人id
     */
    @TableField(value = "create_id", fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建人id")
    private String createId;
    public final static String CREATE_ID = "create_id";

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    public final static String CREATE_TIME = "create_time";

    /**
     * 更新人id
     */
    @TableField(value = "update_id", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="更新人id")
    private String updateId;
    public final static String UPDATE_ID = "update_id";

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;
    public final static String UPDATE_TIME = "update_time";

    /**
     * 逻辑删除 0-否 1-是
     */
    @TableField(value = "is_del", fill = FieldFill.INSERT)
    @TableLogic
    @ApiModelProperty(value="逻辑删除 0-否 1-是")
    private Integer isDel;
    public final static String IS_DEL = "is_del";

}

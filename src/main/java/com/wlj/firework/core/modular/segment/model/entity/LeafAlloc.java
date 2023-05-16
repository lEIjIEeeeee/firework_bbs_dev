package com.wlj.firework.core.modular.segment.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("leaf_alloc")
public class LeafAlloc {

    @TableId("biz_tag")
    private String bizTag;
    private long maxId;
    private int step;
    private String updateTime;
}

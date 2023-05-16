package com.wlj.firework.core.modular.segment.manager;

import com.wlj.firework.core.modular.segment.dao.LeafAllocMapper;
import com.wlj.firework.core.modular.segment.model.entity.LeafAlloc;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LeafAllocManager {

    private final LeafAllocMapper leafAllocMapper;

    public LeafAlloc updateMaxIdAndGetLeafAlloc(String tag) {
        leafAllocMapper.updateMaxId(tag);
        return leafAllocMapper.getLeafAlloc(tag);
    }

    public LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc) {
        leafAllocMapper.updateMaxIdByCustomStep(leafAlloc);
        return leafAllocMapper.getLeafAlloc(leafAlloc.getBizTag());
    }

}

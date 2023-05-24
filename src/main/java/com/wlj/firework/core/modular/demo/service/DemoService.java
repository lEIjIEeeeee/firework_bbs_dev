package com.wlj.firework.core.modular.demo.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlj.firework.core.modular.common.model.request.BaseQueryRequest;
import com.wlj.firework.core.modular.demo.dao.DemoMapper;
import com.wlj.firework.core.modular.demo.manager.DemoManager;
import com.wlj.firework.core.modular.demo.model.entity.Demo;
import com.wlj.firework.core.modular.demo.model.request.DemoDeleteRequest;
import com.wlj.firework.core.modular.demo.model.request.DemoRequest;
import com.wlj.firework.core.modular.common.util.JavaBeanUtils;
import com.wlj.firework.core.modular.common.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class DemoService {

    private final DemoMapper demoMapper;

    private final DemoManager demoManager;

    @Transactional(rollbackFor = Exception.class)
    public void add(DemoRequest request) {
        Demo demo = JavaBeanUtils.map(request, Demo.class);
        demoMapper.insert(demo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(DemoRequest request) {
        Demo demo = demoManager.getByIdWithException(request.getId());
        JavaBeanUtils.map(request, demo);
        demoMapper.updateById(demo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(DemoDeleteRequest request) {
        Demo demo = demoManager.getByIdWithException(request.getId());
        demoMapper.deleteById(demo.getId());
    }

    public Demo get(String id) {
        return demoManager.getByIdWithException(id);
    }

    public Page<Demo> listPage(BaseQueryRequest request) {
        return demoMapper.selectPage(PageUtils.createPage(request), Wrappers.lambdaQuery(Demo.class));
    }

}

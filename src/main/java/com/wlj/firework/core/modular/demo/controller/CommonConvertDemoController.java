package com.wlj.firework.core.modular.demo.controller;

import com.wlj.firework.core.modular.common.model.HttpResult;
import com.wlj.firework.core.modular.common.model.PageVO;
import com.wlj.firework.core.modular.common.model.request.BaseQueryRequest;
import com.wlj.firework.core.modular.demo.service.CommonConvertDemoService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试模块-工具类CommonConvert测试")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/demoModule/CommonConvertTest")
public class CommonConvertDemoController {

    private final CommonConvertDemoService commonConvertDemoService;

    public HttpResult<PageVO<>> listPageWithConvertFunc(@Validated(BaseQueryRequest.ListPage.class)
                                                                BaseQueryRequest request) {

    }
}

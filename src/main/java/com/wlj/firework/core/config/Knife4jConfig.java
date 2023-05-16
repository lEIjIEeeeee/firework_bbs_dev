package com.wlj.firework.core.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class Knife4jConfig {

    @Value("${swagger.enable}")
    private Boolean swaggerEnable;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                                                      .ignoredParameterTypes(HttpSession.class, HttpServletRequest.class)
                                                      .enable(swaggerEnable)
                                                      .select()
                                                      .apis(RequestHandlerSelectors.basePackage("com.wlj.firework.core"))
                                                      .paths(PathSelectors.any())
                                                      .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("测试平台-平台管理API文档")
                                   .description("平台管理服务api")
                                   .version("1.0")
                                   .build();
    }
}

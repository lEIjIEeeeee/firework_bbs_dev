package com.wlj.firework.core;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@MapperScan("com.wlj.firework.core")
@PropertySource({"classpath:application.properties"})
@SpringBootApplication
public class DeployApplication {
    public static void main(String[] args) {
        /*ConfigurableApplicationContext run = SpringApplication.run(DeployApplication.class);*/
        ConfigurableApplicationContext run = new SpringApplicationBuilder(DeployApplication.class).run();
    }
}

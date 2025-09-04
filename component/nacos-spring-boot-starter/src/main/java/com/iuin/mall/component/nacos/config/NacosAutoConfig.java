package com.iuin.mall.component.nacos.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author fa
 */
@AutoConfiguration
@ComponentScan(basePackages = {
        "com.iuin.mall.component.nacos",
})
@Slf4j
public class NacosAutoConfig {

//    /**
//     * 启动时指定环境变量
//     */
//    @PostConstruct
//    public void init() {
//        System.setProperty("nacos.logging.default.config.enabled", "false");
//    }

}

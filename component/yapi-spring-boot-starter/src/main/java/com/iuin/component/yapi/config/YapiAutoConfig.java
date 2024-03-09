package com.iuin.component.yapi.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author fa
 */
@ComponentScan(value = "com.iuin.component.yapi")
@EnableConfigurationProperties(YapiDeployConfig.class)
public class YapiAutoConfig {

}

package com.iuin.shardingsphere.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author fa
 */
@Configuration
@MapperScan("com.iuin.shardingsphere.**.mapper")
public class MybatisConfig {

}

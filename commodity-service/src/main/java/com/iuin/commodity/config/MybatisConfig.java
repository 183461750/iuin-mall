package com.iuin.commodity.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author fa
 */
@Configuration
@MapperScan("com.iuin.commodity.**.mapper")
public class MybatisConfig {

}

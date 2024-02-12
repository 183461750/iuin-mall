package com.iuin.component.test.mysql.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author fa
 */
@Configuration
@MapperScan("com.iuin.component.test.mysql.**.mapper")
public class MybatisConfig {

}

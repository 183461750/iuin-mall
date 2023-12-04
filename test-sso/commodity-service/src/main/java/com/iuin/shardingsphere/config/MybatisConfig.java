package com.iuin.shardingsphere.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author fa
 */
@Configuration
//@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
@MapperScan("com.iuin.commodity.**.mapper")
public class MybatisConfig {

}

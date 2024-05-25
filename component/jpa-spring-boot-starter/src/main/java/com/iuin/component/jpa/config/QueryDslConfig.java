package com.iuin.component.jpa.config;

import com.querydsl.codegen.ClassPathUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.IOException;

/**
 * QueryDSL配置类
 *
 * @author Fa
 */
@ConditionalOnProperty("spring.jpa.database")
public class QueryDslConfig {

    /**
     * JPAQueryFactory配置(因为base组件没有启动类，导致扫描不到包，idea报红，不影响使用)
     */
    @Bean
    @ConditionalOnBean(EntityManager.class)
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    /**
     * 在多线程中使用QEntity进行查询时，有可能会发生死锁，增加此配置解决
     *
     * @throws IOException 初始化异常
     */
    @PostConstruct
    public void init() throws IOException {
        //指定扫描的 Entity 的 package，可以为最上层的 package
        ClassPathUtils.scanPackage(Thread.currentThread().getContextClassLoader(), "com.iuin.**.entity");
    }
}

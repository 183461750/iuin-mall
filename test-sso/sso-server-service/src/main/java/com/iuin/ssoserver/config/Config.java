package com.iuin.ssoserver.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * @author fa
 */
@Configuration
public class Config {

    /**
     * QueryDsl Bean 配置
     * @param entityManager Jpa的EntityManager
     * @return QueryDsl JpaQueryFactory，直接注入使用
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

}

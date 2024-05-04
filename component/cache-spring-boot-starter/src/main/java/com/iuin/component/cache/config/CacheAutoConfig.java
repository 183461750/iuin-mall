package com.iuin.component.cache.config;

import com.iuin.component.base.EnableBaseComponent;
import com.iuin.component.cache.component.CacheComponent;
import com.iuin.component.cache.component.FixedWindowLimitingComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

/**
 * @author fa
 */
@EnableBaseComponent
@Import({CacheComponent.class, FixedWindowLimitingComponent.class})
public class CacheAutoConfig {

//    @Bean
//    public CacheComponent cacheComponent(ReactiveStringRedisTemplate reactiveStringRedisTemplate) {
//        return new CacheComponent(reactiveStringRedisTemplate);
//    }

}

package com.iuin.component.cache.config;

import com.iuin.component.base.annos.EnableBaseComponent;
import com.iuin.component.cache.component.CacheComponent;
import com.iuin.component.cache.component.FixedWindowLimitingComponent;
import org.springframework.context.annotation.Import;

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

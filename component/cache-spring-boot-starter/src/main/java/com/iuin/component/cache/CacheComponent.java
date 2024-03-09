package com.iuin.component.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

import java.time.Duration;

/**
 * @author fa
 */
@AutoConfiguration
@RequiredArgsConstructor
public class CacheComponent {

    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    /**
     * 查询字符串
     */
    public String string(String key) {
        return reactiveStringRedisTemplate.opsForValue().get(key).block();
    }

    /**
     * 保存字符串
     */
    public boolean string(String key, String value) {
        return Boolean.TRUE.equals(reactiveStringRedisTemplate.opsForValue().set(key, value).block());
    }

    /**
     * 保存字符串
     */
    public boolean string(String key, String value, Long seconds) {
        return Boolean.TRUE.equals(reactiveStringRedisTemplate.opsForValue().set(key, value, Duration.ofSeconds(seconds)).block());
    }

}

package com.iuin.component.cache.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.time.Duration;
import java.util.List;

/**
 * @author fa
 */
@AutoConfiguration
@RequiredArgsConstructor
public class CacheComponent {

    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    /**
     * 是否存在
     */
    public Boolean hasKey(String key) {
        return reactiveStringRedisTemplate.hasKey(key).block();
    }

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

    /**
     * 保存字符串
     */
    public Boolean setIsMember(String key, String value) {
        return reactiveStringRedisTemplate.opsForSet().isMember(key, value).block();
    }

    /**
     * 执行lua脚本
     */
    public <T> T executeLua(String luaScript, List<String> keys, List<?> args, Class<T> clazz) {
        return reactiveStringRedisTemplate.execute(RedisScript.of(luaScript, clazz), keys, args).next().block();
    }

}

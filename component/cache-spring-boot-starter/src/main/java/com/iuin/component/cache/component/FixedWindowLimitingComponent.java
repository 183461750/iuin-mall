package com.iuin.component.cache.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

/**
 * 固定窗口限流
 *
 * @author Fa
 */
@Component
@Service
@Slf4j
@RequiredArgsConstructor
public class FixedWindowLimitingComponent {
    private final CacheComponent cacheComponent;
    private static final String LUA_PATH = "scripts/fixedWindowLimiting.lua";

    /**
     * 基于固定窗口的流量监控
     *
     * @param key       redisKey
     * @param threshold 阈值
     * @param seconds   时间间隔（秒）
     * @return true:正常，false:异常
     */
    public Boolean doLimiting(String key, long threshold, long seconds) {
        // 执行lua脚本
        return cacheComponent.executeLua(LUA_PATH, Collections.singletonList(key), Arrays.asList(String.valueOf(threshold), String.valueOf(seconds)), Boolean.class);
    }
}

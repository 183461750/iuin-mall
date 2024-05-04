package com.iuin.component.cache.constants;

import lombok.NoArgsConstructor;

/**
 * @author fa
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RedisConstant {
    /**
     * ip监控 - 白名单前缀
     */
    public static final String IP_MONITOR_WHITELIST = "IP_MONITOR:WHITELIST";

    /**
     * ip监控 - 黑名单前缀
     */
    public static final String IP_MONITOR_BLACKLIST = "IP_MONITOR:BLACKLIST";

}

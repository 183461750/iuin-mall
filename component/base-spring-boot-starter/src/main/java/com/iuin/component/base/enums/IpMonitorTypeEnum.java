package com.iuin.component.base.enums;

import lombok.Getter;

/**
 * ip监控类型
 *
 * @author Fa
 */
@Getter
public enum IpMonitorTypeEnum {
    /**
     * 系统全局流量监控
     */
    SYSTEM("IP_MONITOR:GLOBAL_TRAFFIC:"),

    /**
     * 登录
     */
    LOGIN("IP_MONITOR:LOGIN:"),

    /**
     * 注册
     */
    REGISTER("IP_MONITOR:REGISTER:"),

    /**
     * 发送验证码
     */
    SMS_CODE("IP_MONITOR:SMS_CODE:"),

    /**
     * 个人信息
     */
    PERSON_INFO("IP_MONITOR:PERSON_INFO:");

    private final String redisKeyPrefix;

    IpMonitorTypeEnum(String redisKeyPrefix) {
        this.redisKeyPrefix = redisKeyPrefix;
    }

    public static String generateIpMonitorRedisKey(IpMonitorTypeEnum ipMonitorTypeEnum, String clientIp) {
        return ipMonitorTypeEnum.getRedisKeyPrefix() + clientIp;
    }
}

package com.iuin.component.cache.enums;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.iuin.common.constants.ProjectConstant;
import com.iuin.component.base.holders.EnvHolder;
import lombok.Getter;

/**
 * 缓存key
 *
 * @author fa
 */
@Getter
public enum RedisKeyEnum {

    /**
     * 用户中奖活动全程上限次数
     * {@link #keyPrefix}:{@link #keyOf(Object... variables)}
     */
    USER_ACTIVITY_LOTTERY_LIMIT_COUNT("user:act:lottery:limit") {
        @Override
        public String keyOf(Object... variables) {
            return RedisKeyEnum.keyOf(this, variables);
        }
    },

    /**
     * 用户中奖活动全程上限次数
     * {@link #keyPrefix}:{@link #keyOf(Object... variables)}
     */
    DEPLOY_YAPI_COOKIE("deploy_yapi_Cookie") {
        @Override
        public String keyOf(Object... variables) {
            return RedisKeyEnum.keyOf(this, variables);
        }
    },

    ;

    private final String keyPrefix;

    RedisKeyEnum(String keyPrefix) {
        this.keyPrefix = keyPrefixOf(keyPrefix);
    }

    public abstract String keyOf(Object... variables);

    private static String keyOf(RedisKeyEnum redisKeyEnum, Object... variables) {
        return CharSequenceUtil.join(String.valueOf(CharPool.COLON), CollUtil.toList(redisKeyEnum.keyPrefix, variables));
    }

    private static String keyPrefixOf(String bizKeyPrefix) {
        return CharSequenceUtil.join(StrPool.COLON, ProjectConstant.PROJECT_NAME, EnvHolder.getAppName(), bizKeyPrefix);
    }

}

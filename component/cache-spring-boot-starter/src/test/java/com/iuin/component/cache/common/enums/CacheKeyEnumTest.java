package com.iuin.component.cache.common.enums;

import com.iuin.component.cache.enums.RedisKeyEnum;

/**
 * @author fa
 */
class CacheKeyEnumTest {

    public static void main(String[] args) {

        {
            String key = RedisKeyEnum.USER_ACTIVITY_LOTTERY_LIMIT_COUNT.keyOf(1, 2, 3);

            System.out.println(key);
        }

        {
            String key = RedisKeyEnum.USER_ACTIVITY_LOTTERY_LIMIT_COUNT.keyOf(4, 5, 6);

            System.out.println(key);
        }

        {
            String key = RedisKeyEnum.DEPLOY_YAPI_COOKIE.keyOf();

            System.out.println(key);
        }

    }

}
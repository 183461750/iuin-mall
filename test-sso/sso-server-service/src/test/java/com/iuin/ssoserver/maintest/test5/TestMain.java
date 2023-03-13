package com.iuin.ssoserver.maintest.test5;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;

/**
 * @author fa
 */
public class TestMain {

    public static void main(String[] args) {
        BigDecimal max = NumberUtil.max(null, BigDecimal.ONE);

        System.out.println(max);
    }

}

package com.iuin.common;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author fa
 */
public class cx {

    public static void main(String[] args) {
        DateTime toDate = DateUtil.parse("2023-08-04 23:59:59");

        if (DateUtil.compare(DateUtil.offsetDay(toDate, -1), new Date()) < 0) {
            System.out.println(2);
        }

        System.out.println();
    }

}
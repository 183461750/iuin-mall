package com.iuin.component.test.calc;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author fa
 */
public class Main {

    public static void main(String[] args) {
        {
            BigDecimal bigDecimal = BigDecimal.valueOf(555.55);
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP);
            }
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(3));
            }
            System.out.println(bigDecimal);
        }
        System.out.println("#####################################################################################");
        {
            BigDecimal bigDecimal = BigDecimal.valueOf(555.555555555555555555555555555555555555555555555);
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.divide(BigDecimal.valueOf(3), 14, RoundingMode.HALF_UP);
            }
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(3));
            }
            System.out.println(bigDecimal);
        }
        System.out.println("#####################################################################################");
        {
            BigDecimal bigDecimal = BigDecimal.valueOf(555.55);
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.divide(BigDecimal.valueOf(3), 14, RoundingMode.HALF_UP);
            }
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(3));
            }
            System.out.println(bigDecimal);
        }
        System.out.println("#####################################################################################");
        {
            BigDecimal bigDecimal = BigDecimal.valueOf(555.55);
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.divide(BigDecimal.valueOf(3), 8, RoundingMode.HALF_UP);
            }
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(3));
            }
            System.out.println(bigDecimal);
            // 两位小数时, 这个结果也是对的
        }
        System.out.println("#####################################################################################");
        {
            BigDecimal bigDecimal = BigDecimal.valueOf(555.55555);
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.divide(BigDecimal.valueOf(3), 11, RoundingMode.HALF_UP);
            }
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(3));
            }
            System.out.println(bigDecimal);
            // 五位小数时, 这个结果也是对的
        }
        System.out.println("#####################################################################################");
        {
            BigDecimal bigDecimal = BigDecimal.valueOf(555.55555);
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.divide(BigDecimal.valueOf(3), 8, RoundingMode.HALF_UP);
            }
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(3));
            }
            System.out.println(bigDecimal);
        }
        System.out.println("#####################################################################################");
        {
            BigDecimal bigDecimal = BigDecimal.valueOf(555.55555);
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.divide(BigDecimal.valueOf(3), 9, RoundingMode.HALF_UP);
            }
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(3));
            }
            System.out.println(bigDecimal);
            // 五位小数时, 这个结果也是对的
        }
        System.out.println("#####################################################################################");
        {
            BigDecimal bigDecimal = BigDecimal.valueOf(555.555555555555555555555555555555555555555555555);
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.divide(BigDecimal.valueOf(3), 6, RoundingMode.HALF_UP);
            }
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(3));
            }
            System.out.println(bigDecimal);
        }
        System.out.println("#####################################################################################");
        {
            BigDecimal bigDecimal = BigDecimal.valueOf(555.555555555555555555555555555555555555555555555);
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.divide(BigDecimal.valueOf(3), 5, RoundingMode.HALF_UP);
            }
            for (int i = 0; i < 10; i++) {
                bigDecimal = bigDecimal.multiply(BigDecimal.valueOf(3));
            }
            System.out.println(bigDecimal);
        }
    }

}

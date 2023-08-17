package com.iuin.component.test.test1;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @author fa
 */
public class TestMain3 {

    public static void main(String[] args) {
        {
            String value = "23.124684,13.399227";

            String reversedValue = Arrays.stream(value.split(","))
                    .map(str -> new StringBuilder(str).reverse().toString())
                    .collect(Collectors.joining(","));

            System.out.println(reversedValue);
            System.out.println(StrUtil.reverse(reversedValue));
        }

        System.out.println("-------------------------------------------");

        {
            String value = "23.124684,113.399227";

            String sortedValue = Arrays.stream(value.split(","))
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.joining(","));

            System.out.println(sortedValue);
        }

        System.out.println("-------------------------------------------");

        {
            String value = "23,113";
            String[] split = value.split(",");
            System.out.println(Arrays.toString(split));

            {
                String sortedValue = Arrays.stream(split).sorted(Comparator.naturalOrder()).collect(Collectors.joining(","));
                System.out.println(sortedValue); // 113,23
            }

            String sortedValue = Arrays.stream(split).sorted(Comparator.reverseOrder()).collect(Collectors.joining(","));
            System.out.println(sortedValue); // 23,113

        }

        System.out.println("-------------------------------------------");

        {
            String value = "13,213";
            String[] split = value.split(",");
            System.out.println(Arrays.toString(split));

            {
                String sortedValue = Arrays.stream(split).sorted(Comparator.naturalOrder()).collect(Collectors.joining(","));
                System.out.println(sortedValue); // 13,213
            }

            String sortedValue = Arrays.stream(split).sorted(Comparator.reverseOrder()).collect(Collectors.joining(","));
            System.out.println(sortedValue); // 213,13

        }

        System.out.println("-------------------------------------------");

        {
            Integer[] split = {13,213};
            System.out.println(Arrays.toString(split));

            {
                String sortedValue = Arrays.stream(split).sorted(Comparator.naturalOrder()).map(String::valueOf).collect(Collectors.joining(","));
                System.out.println(sortedValue); // 13,213
            }

            String sortedValue = Arrays.stream(split).sorted(Comparator.reverseOrder()).map(String::valueOf).collect(Collectors.joining(","));
            System.out.println(sortedValue); // 213,13

        }

        System.out.println("-------------------------------------------");

        {
            Integer[] split = {23,113};
            System.out.println(Arrays.toString(split));

            {
                String sortedValue = Arrays.stream(split).sorted(Comparator.naturalOrder()).map(String::valueOf).collect(Collectors.joining(","));
                System.out.println(sortedValue); // 23,113
            }

            String sortedValue = Arrays.stream(split).sorted(Comparator.reverseOrder()).map(String::valueOf).collect(Collectors.joining(","));
            System.out.println(sortedValue); // 113,23

        }



        System.out.println("-------------------------------------------");

        {
            String value = "13,213";
            String[] split = value.split(",");
            System.out.println(Arrays.toString(split));

            {
                String sortedValue = Arrays.stream(split).sorted(Comparator.naturalOrder()).collect(Collectors.joining(","));
                System.out.println(sortedValue); // 13,213
            }

            String sortedValue = Arrays.stream(split).sorted(Comparator.reverseOrder()).collect(Collectors.joining(","));
            System.out.println(sortedValue); // 213,13

            System.out.println("xxx");
            {
                String decode = URLUtil.decode("13%2C213", StandardCharsets.UTF_8);
                System.out.println("decode:" + decode);
                System.out.println(String.join(",", CollUtil.reverse(StrUtil.split(decode, ","))));
            }
            
            String decode = URLUtil.decode("13,213", StandardCharsets.UTF_8);
            System.out.println("decode:" + decode);
            System.out.println(String.join(",", CollUtil.reverse(StrUtil.split(decode, ","))));

            System.out.println(String.join(",", CollUtil.reverse(StrUtil.split("13,213", ","))));
            System.out.println(String.join(",", CollUtil.reverse(StrUtil.split("23,113", ","))));
            System.out.println(String.join(",", CollUtil.reverse(StrUtil.split("23", ","))));
            System.out.println(String.join(",", CollUtil.reverse(StrUtil.split("23,", ","))));
            System.out.println(String.join(",", CollUtil.reverse(StrUtil.split("", ","))));

        }


    }

}

package com.iuin.component.test.hutools;

import cn.hutool.core.util.StrUtil;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fa
 */
public class Main {

    public static void main(String[] args) {
//        StrUtil.isWrap()
        {
            String str = "Hello #@[world] how [are] you [doing]";
            StringTokenizer tokenizer = new StringTokenizer(str, "#@[]");
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                if (!token.isEmpty()) {
                    System.out.println(token); // Output: world, are, doing
                }
            }
        }
        System.out.println("######################################################");
        {
            String str = "Hello #@[world] how [are] you #@[doing]";
//            String pattern = "\\#\\@\\[[^\\]]*\\]";
            String pattern = "\\#\\@\\[[^\\]]*\\]";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(str);
            while (matcher.find()) {
                String substring = matcher.group().substring(1, matcher.group().length() - 1);
                System.out.println(substring); // Output: world, are, doing
            }
        }
    }

}

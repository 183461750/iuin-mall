package com.iuin.component.test.test1;

/**
 * <p>TODO</p>
 *
 * @author fa
 */
public class TestMain2 {

    public static void main(String[] args) {
        String a = "1_b_2";
        int i = a.indexOf("_");

        System.out.println(i);
        System.out.println(a.lastIndexOf("_"));

        System.out.println(a.substring(0, a.lastIndexOf("_")));
        System.out.println(a.substring(a.lastIndexOf("_")+1));
        System.out.println(a.substring(Math.min(a.lastIndexOf("_")+100, a.length())));
        System.out.println(a.substring(a.length()));
    }

}

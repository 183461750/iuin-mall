package com.iuin.component.test.take_time_processor;

/**
 * @author fa
 */
public class Main {

    public static void main(String[] args) {
        {
            PigTakeTimeTest pig = new PigTakeTimeTest();
            pig.setAge(18);
            pig.setName("little pig");

            System.out.println(pig);
        }
        System.out.println("#########################################");
        {
            PigTakeTimeTestTakeTime pig = new PigTakeTimeTestTakeTime();
            pig.setAge(18);
            pig.setName("TakeTime pig");

            System.out.println(pig);
        }

    }

}

package com.iuin.component.test.take_time_processor;

import com.iuin.component.pluggable_annotation.take_time.TakeTime;
import com.iuin.component.pluggable_annotation.take_time.model.Pig;
import lombok.ToString;

/**
 * @author fa
 */
@ToString
public class PigTakeTimeTest extends Pig {

    private Integer age;

    @TakeTime(tag = "age")
    public void setAge(Integer age) {
        this.age = age;
    }

}

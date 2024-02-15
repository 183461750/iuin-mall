package com.iuin.component.test.take_time_processor;

import com.iuin.component.pluggable_annotation.take_time.TakeTime;
import com.iuin.component.pluggable_annotation.take_time.model.Pig;
import com.iuin.component.pluggable_annotation.version.TrisceliVersion;
import lombok.ToString;

/**
 * @author fa
 */
@ToString(callSuper = true)
public class PigTakeTimeTest extends Pig {

    private Integer age;

    @TrisceliVersion
    private String version;

    @TakeTime(tag = "age")
    public void setAge(Integer age) {
        this.age = age;
    }

}

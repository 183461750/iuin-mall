package com.iuin.component.pluggable_annotation.take_time.model;

import com.iuin.component.pluggable_annotation.take_time.TakeTime;
import lombok.ToString;

/**
 * @author fa
 */
@ToString
public class Pig {

    private String name;

    @TakeTime(tag = "xxx")
    public void setName(String name) {
        this.name = name;
    }


}

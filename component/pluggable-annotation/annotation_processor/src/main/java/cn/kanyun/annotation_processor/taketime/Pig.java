package cn.kanyun.annotation_processor.taketime;

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

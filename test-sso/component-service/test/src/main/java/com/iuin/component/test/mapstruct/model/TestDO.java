package com.iuin.component.test.mapstruct.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author fa
 */
@Data
public class TestDO {

    private String name;

    private Integer age;

    private LocalDateTime createTime;

}

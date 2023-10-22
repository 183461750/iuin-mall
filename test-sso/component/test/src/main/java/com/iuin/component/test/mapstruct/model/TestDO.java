package com.iuin.component.test.mapstruct.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * @author fa
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class TestDO {

    private String name;

    private Integer age;

    private LocalDateTime createTime;

    private Boolean isDefault;

}

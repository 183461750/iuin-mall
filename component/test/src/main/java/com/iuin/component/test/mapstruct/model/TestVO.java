package com.iuin.component.test.mapstruct.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @author fa
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class TestVO {

    private String name;

    private Integer userAge;

    private String userCreateTime;

    private Integer isDefault;

}

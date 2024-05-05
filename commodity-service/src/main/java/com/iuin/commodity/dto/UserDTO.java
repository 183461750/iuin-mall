package com.iuin.commodity.dto;

import lombok.Data;

/**
 * 自定义resultType
 * @author fa
 */
@Data
public class UserDTO {
    private Long id;
    private String name;
    private Integer age;
    private String email;

    private String city;
    private String address;
}
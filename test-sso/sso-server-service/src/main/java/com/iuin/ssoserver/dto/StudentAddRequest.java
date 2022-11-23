package com.iuin.ssoserver.dto;

import lombok.Data;

/**
 * @author fa
 */
@Data
public class StudentAddRequest {

    private Integer age;

    private Integer sex;

    private String name;


    private String province;

    private String city;

    private String area;


    private String roleName;


    private Integer petAge;

    private Integer petSex;

    private String petName;


}

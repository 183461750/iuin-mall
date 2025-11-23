package com.iuin.component.test.compile_time_code_gen;

import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassMetaConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * 测试类，用于验证ClassMetaConstants注解的编译期代码生成功能
 */
@Setter
@Getter
@FieldNameConstants
@ClassMetaConstants(innerClassName = "UserConstants")
public class User {

    // Getter和Setter方法
    private String username;
    private String email;
    private Integer age;
    private Boolean active;

    // 构造方法
    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // 自定义方法
    public String getFullInfo() {
        return "User{username='" + username + "', email='" + email + "'}";
    }
}

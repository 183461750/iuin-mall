package com.iuin.component.test.compile_time_code_gen;

import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassSignatureConstants;

/**
 * 测试类，用于验证ClassSignatureConstants注解的编译期代码生成功能
 */
@ClassSignatureConstants(innerClassName = "UserConstants")
public class User {
    
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
    
    // Getter和Setter方法
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    // 自定义方法
    public String getFullInfo() {
        return "User{username='" + username + "', email='" + email + "'}";
    }
}

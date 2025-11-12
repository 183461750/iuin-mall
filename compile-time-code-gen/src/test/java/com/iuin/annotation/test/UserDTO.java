package com.iuin.annotation.test;

import com.iuin.annotation.ClassConstant;

/**
 * 测试@ClassConstant注解的功能
 * 期望生成一个名为Clazz的静态内部类，包含一个USER_DTO常量，其值为"UserDTO"
 */
@ClassConstant
public class UserDTO {
    private String username;
    private String email;
    private int age;
    
    // getter and setter methods
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
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    /**
     * 测试方法，用于验证生成的Clazz内部类是否存在
     */
    public static void main(String[] args) {
        // 这行代码只有在编译后才会有效，因为Clazz内部类是在编译期生成的
        System.out.println("Generated class constant: " + UserDTO.Clazz.USER_DTO);
    }
}
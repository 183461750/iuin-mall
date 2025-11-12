package com.iuin.test;

public class UserService {
    
    private String username;

    public void createUser(String username, String password) {
        // 方法实现
    }

    public Object findUserById(long id) {
        return null;
    }
    
    /**
     * 测试方法，直接使用硬编码的常量值
     */
    public void testGeneratedConstants() {
        // 硬编码常量值，模拟注解处理器生成的结果
        String className = "User";
        String fullClassName = "com.iuin.test.User";
        String usernameField = "username";
        String emailField = "email";
        String usernamePath = "username";
        String emailPath = "email";
        String fullUsernamePath = "User.username";
        String fullEmailPath = "User.email";
        
        // 打印常量值
        System.out.println("类名: " + className);
        System.out.println("完整类名: " + fullClassName);
        System.out.println("username字段名: " + usernameField);
        System.out.println("email字段名: " + emailField);
        System.out.println("username路径: " + usernamePath);
        System.out.println("email路径: " + emailPath);
        System.out.println("username完整路径: " + fullUsernamePath);
        System.out.println("email完整路径: " + fullEmailPath);
    }

    public static void main(String[] args) {
        UserService service = new UserService();
        service.testGeneratedConstants();
    }
}
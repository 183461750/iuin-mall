package com.iuin.component.test.compile_time_code_gen;

/**
 * UserService类，用于测试生成的ClassSignatureConstants常量
 */
public class UserService {
    
    private String username;

    public void createUser(String username, String password) {
        // 方法实现
    }

    public Object findUserById(long id) {
        return null;
    }
    
    /**
     * 测试方法，使用生成的常量
     * 这里会在编译期使用User类中自动生成的UserConstants内部类
     */
    public void testGeneratedConstants() {
        // 访问生成的类名常量
        String simple = User_UserConstants.SIMPLE_CLASS_NAME;
        String full = User_UserConstants.CLASS_NAME;

        // 打印常量值
        System.out.println("简单类名: " + simple);
        System.out.println("完整类名: " + full);
    }

    public static void main(String[] args) {
        UserService service = new UserService();
        service.testGeneratedConstants();
    }
}

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
        String className = User.UserConstants.CLASS_NAME;
        String fullClassName = User.UserConstants.FULL_CLASS_NAME;
        
        // 访问生成的字段名常量
        String usernameField = User.UserConstants.FIELD_USERNAME;
        String emailField = User.UserConstants.FIELD_EMAIL;
        
        // 访问生成的字段路径常量
        String usernamePath = User.UserConstants.PATH_USERNAME;
        String emailPath = User.UserConstants.PATH_EMAIL;
        
        // 访问生成的完整字段路径常量
        String fullUsernamePath = User.UserConstants.FULL_PATH_USERNAME;
        String fullEmailPath = User.UserConstants.FULL_PATH_EMAIL;
        
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

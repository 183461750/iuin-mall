package com.iuin.component.test.compile_time_code_gen;

import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassSignatureConstants;
import lombok.experimental.FieldNameConstants;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@FieldNameConstants
@ClassSignatureConstants
public class UserService {

    private String username;

    public void createUser(String username, String password) {
        // 方法实现
    }

    public SecurityProperties.User findUserById(long id) {
        return null;
    }

    public static void main(String[] args) {

        // 使用生成的常量
        String className = UserService.Signatures.CLASS_NAME; // "包名.UserService"
        String methodSignature = UserService.Signatures.CREATE_USER; // "void createUser(String, String)"

        System.out.println(className);
        System.out.println(methodSignature);


        String username = UserService.Fields.username;
        System.out.println(username);
    }
}

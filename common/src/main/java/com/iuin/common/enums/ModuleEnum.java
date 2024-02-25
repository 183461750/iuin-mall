package com.iuin.common.enums;

import com.iuin.common.constants.ModuleConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * 项目部署
 *
 * @author whm
 **/
@Getter
@RequiredArgsConstructor
public enum ModuleEnum {

    SSO_SERVER_SERVICE(1, "单点登录服务", ModuleConstant.SSO_SERVER_SERVICE),
    ;

    private final Integer code;
    private final String cnName;
    private final String name;

    public static String getCnName(String moduleName) {
        ModuleEnum moduleEnum = Arrays.stream(ModuleEnum.values()).filter(v -> v.name.equals(moduleName)).findAny().orElse(null);
        return null == moduleEnum ? "未知服务" : moduleEnum.cnName;
    }
}

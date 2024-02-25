package com.iuin.component.yapi.common.enums;


/**
 * 项目部署-yapi-uri
 * @author fhj
 * @since 2021/04/26
 * @version 2.0.0
 */
public enum YapiDeployUriEnum {
    //登录
    LOGIN(1, "/api/user/login"),
    //展示组下所有项目
    MODULE_LIST(2, "/api/project/list?group_id=%s&page=1&limit=9999"),
    //查询模块token
    MODULE_TOKEN(3, "/api/project/token?project_id=%s"),
    //新增分组
    ADD_GROUP(4, "/api/group/add"),
    //新增项目
    ADD_PROJECT(5, "/api/project/add"),
    //展示列表
    GROUP_LIST(6, "/api/group/list"),
    ;

    private final Integer code;
    private final String message;

    YapiDeployUriEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

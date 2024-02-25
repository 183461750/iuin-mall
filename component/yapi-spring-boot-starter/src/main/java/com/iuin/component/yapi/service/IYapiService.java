package com.iuin.component.yapi.service;

import com.iuin.component.yapi.model.*;

import java.util.List;

/**
 * yapi服务通用接口
 *
 * @author whm
 * @version 2.0.0
 * @since 2023-04-14
 **/
public interface IYapiService {
    /**
     * 检查yapi服务基础配置
     */
    boolean checkInitYapiConfig();

    /**
     * 检查yapi服务基础配置
     */
    boolean checkInitYapiConfig(List<String> configs);

    /**
     * 登录
     */
    String doLogin();

    /**
     * 获取登录cookie
     */
    String yapiLogin();

    /**
     * 获取cookie
     */
    String getLoginCookie(String cookie);

    /**
     * 添加分组
     */
    AddGroupResp addGroup(String groupName, String groupDesc);

    /**
     * 添加项目
     */
    AddProjectResp addProject(String groupId, String projectName);

    /**
     * 展示分组信息
     */
    List<ListGroupResp> listGroup();

    /**
     * 展示项目信息
     */
    List<ListProjectResp> listProject(String groupId);

    /**
     * 获取项目token（因为yapi接口返回的数据里只有token，为了知道是哪个项目的，所以只能在循环里获取）
     */
    YapiTokenListResp listProjectTokens(String projectId, String name);
}

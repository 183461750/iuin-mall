package com.iuin.component.yapi.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.component.base.exceptions.BusinessException;
import com.iuin.component.cache.component.CacheComponent;
import com.iuin.component.yapi.common.enums.YapiDeployUriEnum;
import com.iuin.component.yapi.config.YapiDeployConfig;
import com.iuin.component.yapi.model.*;
import com.iuin.component.yapi.service.IYapiService;
import com.iuin.component.yapi.utils.BusinessAssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasLength;

/**
 * yapi服务实现类
 *
 * @author whm
 * @version 2.0.0
 * @since 2023-04-14
 **/
@Service
@Slf4j
public class YapiServiceImpl extends AbstractYapiService implements IYapiService {

    public YapiServiceImpl(YapiDeployConfig deployConfig, CacheComponent cacheComponent) {
        super(deployConfig, cacheComponent);
    }

    @Override
    public String yapiLogin() {
        return Optional.ofNullable(getCookieFromCache()).orElseGet(this::doLogin);
    }

    @Override
    public String getLoginCookie(String cookie) {
        return hasLength(cookie) ? cookie : yapiLogin();
    }

    @Override
    public List<ListGroupResp> listGroup() {
        // 获取cookie
        String cookie = yapiLogin();

        // 获取请求url
        String groupList = getRequestUrl(YapiDeployUriEnum.GROUP_LIST.getMessage());

        // 发起请求
        HttpResponse listGroupHttpResponse = HttpUtil.createGet(groupList)
                .header(HttpHeaders.COOKIE, cookie)
                .keepAlive(true)
                .setReadTimeout(10 * 1000)
                .execute();

        // 如果状态码不是200则报错
        BusinessAssertUtil.isTrue(HttpStatus.HTTP_OK == listGroupHttpResponse.getStatus(), ResponseCodeEnum.MAN_SERVICE_PLATFORM_DEPLOY_YAPI_REQUEST_ERROR);

        // 如果请求成功，提取Response请求的请求体
        YapiBaseResp<String> yapiBaseResponse = JSONUtil.toBean(listGroupHttpResponse.body(), new TypeReference<YapiBaseResp<String>>() {
        }, false);

        // 如果返回码不是请求成功则报错（yapi请求成功码默认为0）
        if (yapiBaseResponse.getErrcode() != YAPI_SUCCESS_CODE) {
            log.warn("项目部署 >> 请求Yapi失败 url:{}  body:{}", groupList, listGroupHttpResponse.body());
            throw new BusinessException(yapiBaseResponse.getErrmsg());
        }

        return JSONUtil.parseArray(yapiBaseResponse.getData()).toList(ListGroupResp.class);
    }

    @Override
    public List<ListProjectResp> listProject(String groupId) {
        // 获取cookie
        String cookie = yapiLogin();

        // 获取请求url
        String listProjectUrl = getRequestUrl(YapiDeployUriEnum.MODULE_LIST.getMessage());

        // 发起请求
        HttpResponse listProjectHttpResponse = HttpUtil.createGet(String.format(listProjectUrl, groupId))
                .header(HttpHeaders.COOKIE, cookie)
                .keepAlive(true)
                .setReadTimeout(10 * 1000)
                .execute();

        // 如果状态码不是200则报错
        BusinessAssertUtil.isTrue(HttpStatus.HTTP_OK == listProjectHttpResponse.getStatus(), ResponseCodeEnum.MAN_SERVICE_PLATFORM_DEPLOY_YAPI_REQUEST_ERROR);

        // 如果请求成功，提取Response请求的请求体
        YapiBaseResp<String> yapiBaseResponse = JSONUtil.toBean(listProjectHttpResponse.body(), new TypeReference<YapiBaseResp<String>>() {
        }, false);

        // 如果返回码不是请求成功则报错（yapi请求成功码默认为0）
        if (yapiBaseResponse.getErrcode() != YAPI_SUCCESS_CODE) {
            log.warn("项目部署 >> 请求Yapi失败 url:{}  body:{}", listProjectUrl, listProjectHttpResponse.body());
            throw new BusinessException(yapiBaseResponse.getErrmsg());
        }

        return JSONUtil.parseArray(JSONUtil.parse(yapiBaseResponse.getData()).getByPath("list")).toList(ListProjectResp.class);
    }

    @Override
    public YapiTokenListResp listProjectTokens(String projectId, String name) {
        // 获取cookie
        String cookie = yapiLogin();

        // 获取请求url
        String listProjectTokensUrl = getRequestUrl(YapiDeployUriEnum.MODULE_TOKEN.getMessage());

        // 发起请求
        HttpResponse listProjectTokensHttpResponse = HttpUtil.createGet(String.format(listProjectTokensUrl, projectId))
                .header(HttpHeaders.COOKIE, cookie)
                .keepAlive(true)
                .setReadTimeout(10 * 1000)
                .execute();

        // 如果状态码不是200则报错
        BusinessAssertUtil.isTrue(HttpStatus.HTTP_OK == listProjectTokensHttpResponse.getStatus(), ResponseCodeEnum.MAN_SERVICE_PLATFORM_DEPLOY_YAPI_REQUEST_ERROR);

        // 如果请求成功，提取Response请求的请求体
        YapiBaseResp<String> yapiBaseResponse = JSONUtil.toBean(listProjectTokensHttpResponse.body(), new TypeReference<YapiBaseResp<String>>() {
        }, false);

        // 如果返回码不是请求成功则报错（yapi请求成功码默认为0）
        if (yapiBaseResponse.getErrcode() != YAPI_SUCCESS_CODE) {
            log.warn("项目部署 >> 请求Yapi失败 url:{}  body:{}", listProjectTokensUrl, listProjectTokensHttpResponse.body());
            throw new BusinessException(yapiBaseResponse.getErrmsg());
        }

        return new YapiTokenListResp(name, yapiBaseResponse.getData());
    }

    @Override
    public AddGroupResp addGroup(String groupName, String groupDesc) {
        // 获取cookie
        String cookie = yapiLogin();

        // 获取请求url
        String addGroupUrl = getRequestUrl(YapiDeployUriEnum.ADD_GROUP.getMessage());

        // 构建请求体参数
        JSONObject paramJSONObject = new JSONObject()
                .putOpt("group_name", groupName)
                .putOpt("group_desc", groupDesc)
                .putOpt("owner_uids", JSONUtil.createArray());

        // 发起请求
        HttpResponse addGroupHttpResponse = HttpUtil.createPost(addGroupUrl)
                .header(HttpHeaders.COOKIE, cookie)
                .keepAlive(true)
                .body(paramJSONObject.toString())
                .setReadTimeout(10 * 1000)
                .execute();

        // 如果状态码不是200则报错
        BusinessAssertUtil.isTrue(HttpStatus.HTTP_OK == addGroupHttpResponse.getStatus(), ResponseCodeEnum.MAN_SERVICE_PLATFORM_DEPLOY_YAPI_REQUEST_ERROR);

        // 如果请求成功，提取Response请求的请求体
        YapiBaseResp<String> yapiBaseResponse = JSONUtil.toBean(addGroupHttpResponse.body(), new TypeReference<YapiBaseResp<String>>() {
        }, false);

        // 如果返回码不是请求成功则报错（yapi请求成功码默认为0）
        if (yapiBaseResponse.getErrcode() != YAPI_SUCCESS_CODE) {
            log.warn("项目部署 >> 请求Yapi失败 url:{}  body:{}", addGroupUrl, addGroupHttpResponse.body());
            throw new BusinessException(yapiBaseResponse.getErrmsg());
        }

        return JSONUtil.toBean(yapiBaseResponse.getData(), AddGroupResp.class);
    }

    @Override
    public AddProjectResp addProject(String groupId, String projectName) {
        // 获取cookie
        String cookie = yapiLogin();

        // 获取请求url
        String addProjectUrl = getRequestUrl(YapiDeployUriEnum.ADD_PROJECT.getMessage());

        // 构建请求体参数
        JSONObject paramJSONObject = new JSONObject()
                .putOpt("basepath", "")
                .putOpt("color", "")
                .putOpt("icon", "")
                .putOpt("group_id", groupId)
                .putOpt("name", projectName)
                .putOpt("project_type", "private");

        // 发起请求
        HttpResponse addProjectHttpResponse = HttpUtil.createPost(addProjectUrl)
                .header(HttpHeaders.COOKIE, cookie)
                .keepAlive(true)
                .body(paramJSONObject.toString())
                .setReadTimeout(10 * 1000)
                .execute();

        // 如果状态码不是200则报错
        BusinessAssertUtil.isTrue(HttpStatus.HTTP_OK == addProjectHttpResponse.getStatus(), ResponseCodeEnum.MAN_SERVICE_PLATFORM_DEPLOY_YAPI_REQUEST_ERROR);

        // 如果请求成功，提取Response请求的请求体
        YapiBaseResp<String> yapiBaseResponse = JSONUtil.toBean(addProjectHttpResponse.body(), new TypeReference<YapiBaseResp<String>>() {
        }, false);

        // 如果返回码不是请求成功则报错（yapi请求成功码默认为0）
        if (yapiBaseResponse.getErrcode() != YAPI_SUCCESS_CODE) {
            log.warn("项目部署 >> 请求Yapi失败 url:{}  body:{}", addProjectUrl, addProjectHttpResponse.body());
            throw new BusinessException(yapiBaseResponse.getErrmsg());
        }

        return JSONUtil.toBean(yapiBaseResponse.getData(), AddProjectResp.class);
    }

}

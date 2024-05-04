package com.iuin.component.yapi.service.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.component.base.exceptions.BusinessException;
import com.iuin.component.cache.component.CacheComponent;
import com.iuin.component.cache.enums.RedisKeyEnum;
import com.iuin.component.yapi.common.enums.YapiDeployUriEnum;
import com.iuin.component.yapi.config.YapiDeployConfig;
import com.iuin.component.yapi.model.YapiBaseResp;
import com.iuin.component.yapi.service.IYapiService;
import com.iuin.component.yapi.utils.BusinessAssertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * yapi服务抽象类
 *
 * @author whm
 **/
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractYapiService implements IYapiService {

    protected final YapiDeployConfig deployConfig;
    protected final CacheComponent cacheComponent;

    public static final ThreadLocal<String> cookieHolder = new ThreadLocal<>();
    public static final int YAPI_SUCCESS_CODE = 0;

    @Override
    public String doLogin() {
        // 获取请求url
        String loginUrl = getRequestUrl(YapiDeployUriEnum.LOGIN.getMessage());

        // 构造登录请求体
        String loginRequestBodyJsonString = new JSONObject().putOpt("email", deployConfig.getEmail())
                .putOpt("password", deployConfig.getPassword())
                .toString();

        // 发送登录请求，获取返回对象
        HttpResponse loginHttpResponse = HttpUtil.createPost(loginUrl)
                .body(loginRequestBodyJsonString, ContentType.JSON.toString())
                .keepAlive(true)
                .setReadTimeout(10 * 1000)
                .execute();

        // 如果状态码不是200则报错
        BusinessAssertUtil.isTrue(HttpStatus.HTTP_OK == loginHttpResponse.getStatus(), ResponseCodeEnum.MAN_SERVICE_PLATFORM_DEPLOY_YAPI_REQUEST_ERROR);

        // 如果请求成功，提取Response请求的请求体
        YapiBaseResp<String> yapiBaseResponse = JSONUtil.toBean(loginHttpResponse.body(), new TypeReference<>() {
        }, false);

        // 如果返回码不是请求成功则报错（yapi请求成功码默认为0）
        if (yapiBaseResponse.getErrcode() != YAPI_SUCCESS_CODE) {
            log.warn("项目部署 >> 登录Yapi失败 url:{}  body:{}", loginUrl, loginHttpResponse.body());
            throw new BusinessException(yapiBaseResponse.getErrmsg());
        }

        // 提取cookie并转成json字符
        String cookiesJsonStr = JSONUtil.toJsonStr(String.join(";", loginHttpResponse.headerList(HttpHeaders.SET_COOKIE)));

        // 将登录cookie存到redis
        cacheComponent.string(RedisKeyEnum.DEPLOY_YAPI_COOKIE.keyOf(), cookiesJsonStr, 30 * 60L);//默认半小时
        log.info("项目部署 >> login:cookies: {}", cookiesJsonStr);

        // 存入ThreadLocalMap
        cookieHolder.set(cookiesJsonStr);

        return cookiesJsonStr;
    }

    protected String getRequestUrl(String path) {
        return deployConfig.getServerUrl() + path;
    }

    @Override
    public boolean checkInitYapiConfig() {
        return checkInitYapiConfig(deployConfig.getConfigList());
    }

    @Override
    public boolean checkInitYapiConfig(List<String> configs) {
        return Objects.nonNull(configs) && configs.stream().noneMatch(StringUtils::hasText);
    }

    protected String getCookieFromThreadLocal() {
        return cookieHolder.get();
    }

    protected String getCookieFromRedis() {
        return Optional.ofNullable(cacheComponent.string(RedisKeyEnum.DEPLOY_YAPI_COOKIE.keyOf()))
                .map(cookie -> {
                    cookieHolder.set(cookie);
                    return cookie;
                }).orElse(null);
    }

    protected String getCookieFromCache() {
        return Optional.ofNullable(getCookieFromThreadLocal())
                .orElse(getCookieFromRedis());
    }

    public String getGroupId() {
        return deployConfig.getGroupId();
    }

    public String getServerUrl() {
        return deployConfig.getServerUrl();
    }
}

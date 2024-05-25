package com.iuin.common.constants;

import cn.hutool.core.text.CharPool;

import java.io.Serializable;

/**
 * 模块相关常量
 *
 * @author fa
 */
public class ModuleConstant implements Serializable {
    private static final long serialVersionUID = -7975984841516878213L;

    /**
     * 服务
     */
    public static final String SERVICE = "service";

    /**
     * 内部feign
     */
    public static final String FEIGN = "feign";

    /**
     * 单点登录服务
     */
    public static final String SSO_SERVER = "sso-server";

    /**
     * 支撑
     */
    public static final String SUPPORT = "support";

    /**
     * 工作流任务
     */
    public static final String WORKFLOW = "workflow";

    /**
     * api
     */
    public static final String API = "api";

    /**
     * 商品
     */
    public static final String COMMODITY = "commodity";

    /**
     * 搜索
     */
    public static final String SEARCH = "search";

    /**
     * 单点登录服务名
     */
    public static final String SSO_SERVER_SERVICE = SSO_SERVER + CharPool.DASHED + SERVICE;

    /**
     * 商品服务名
     */
    public static final String COMMODITY_SERVICE = COMMODITY + CharPool.DASHED + SERVICE;

    /**
     * 搜索服务名
     */
    public static final String SEARCH_SERVICE = SEARCH + CharPool.DASHED + SERVICE;

    /**
     * 单点登录服务接口路径前缀
     */
    public static final String SSO_SERVER_PATH_PREFIX = CharPool.SLASH + SSO_SERVER;

    /**
     * 单点登录服务内部接口路径前缀
     */
    public static final String SSO_SERVER_FEIGN_PATH_PREFIX = CharPool.SLASH + SSO_SERVER + CharPool.SLASH + FEIGN;

    /**
     * 商品服务接口路径前缀
     */
    public static final String COMMODITY_PATH_PREFIX = CharPool.SLASH + COMMODITY;

    /**
     * 搜索服务接口路径前缀
     */
    public static final String SEARCH_PATH_PREFIX = CharPool.SLASH + SEARCH;

    /**
     * 搜索服务接口路径前缀
     */
    public static final String SEARCH_FEIGN_PATH_PREFIX = CharPool.SLASH + SEARCH + CharPool.SLASH + FEIGN;

}

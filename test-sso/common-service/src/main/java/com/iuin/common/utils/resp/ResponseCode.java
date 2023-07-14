package com.iuin.common.utils.resp;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;

/**
 * 统一返回编码枚举
 *
 * @author fa
 */
public enum ResponseCode {

    //****************************************
    //****** 通用 状态码************
    //****************************************

    /**
     * 成功
     */
    SUCCESS(1000, "操作成功"),

    /**
     * 在Redis中没有Token（已经过期、用户没登录），请先登录
     */
    TOKEN_DOES_NOT_EXIST_IN_CACHE(1101, "登录已过期，请重新登录"),

    /**
     * 参数格式错误
     */
    REQUEST_PARAM_CHECK_FAILED(1102, "参数校验或转换错误"),

    /**
     * 不支持的Http请求方式
     */
    REQUEST_METHOD_NOT_SUPPORTED(1103, "不支持的Http请求方法"),
    /**
     * 不支持的Http Content-Type
     */
    REQUEST_CONTENT_TYPE_NOT_SUPPORTED(1104, "不支持的Http Content-Type设置"),


    /**
     * 前端发送Http Post Json请求时，接口所需的参数没有传递
     */
    REQUEST_BODY_IS_MISSING(1105, "请求体参数解析异常"),

    /**
     * 用户账号或密码错误
     */
    USER_ACCOUNT_OR_PASSWORD_INCORRECT(1200, "用户账号或密码错误"),

    /**
     * 用户登录时，账号已经被冻结或禁用
     */
    USER_ACCOUNT_HAS_BEEN_FROZEN(1203, "账号已被冻结"),

    /**
     * 网关 - 对后台接口做权限认证时，无权限访问返回的错误码
     */
    UNAUTHORIZED_REQUEST(1210, "无访问权限"),

    /**
     * 服务内部错误（未处理的异常默认返回）
     */
    BUSINESS_ERROR(9999, "服务内部错误"),

    /**
     * 服务内部错误（未处理的异常默认返回）
     */
    SERVICE_ERROR(9998, "业务异常"),

    /**
     * 服务之间OpenFeign接口无法访问
     */
    FEIGN_SERVICE_ERROR(9997, "OpenFeign 服务异常."),

    /**
     * 无此数据权限
     */
    DATA_AUTH_NOT_ALLOWED(9995, "无此数据权限"),

    /**
     * 请求参数校验异常
     */
    REQUEST_PARAMETER_VALIDATION_EXCEPTION(2007, "请求参数校验异常"),

    /**
     * 请求sso中央服务失败
     */
    SERVICE_SSO_SERVER_ERROR(2011, "请求sso中央服务失败"),

    /**
     * 获取锁超时
     */
    SERVICE_LOCK_TIME_OUT(2023, "系统繁忙，请稍后再试"),

    ;


    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(int code) {
        ResponseCode responseCode = Arrays.stream(ResponseCode.values()).filter(r -> r.getCode() == code).findFirst().orElse(null);
        if (responseCode != null) {
            return responseCode.getMessage();
        }
        return null;
    }

    public static String getMessage(int code, String msg) {
        ResponseCode responseCode = Arrays.stream(ResponseCode.values()).filter(r -> r.getCode() == code).findFirst().orElse(null);
        if (responseCode != null) {
            if (responseCode.getMessage().contains("%s") && !responseCode.getMessage().equals(msg)) {
                return String.format(responseCode.getMessage(), msg);
            }
            return StrUtil.isNotBlank(msg) ? msg : responseCode.getMessage();
        }
        return null;
    }

    public static ResponseCode getByCode(int code) {
        return Arrays.stream(ResponseCode.values()).filter(r -> r.getCode() == code).findFirst().orElse(BUSINESS_ERROR);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}

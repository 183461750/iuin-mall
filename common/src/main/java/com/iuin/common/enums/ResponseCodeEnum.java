package com.iuin.common.enums;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.Getter;

import java.util.Arrays;

/**
 * 统一返回编码枚举
 *
 * @author fa
 */
@Getter
public enum ResponseCodeEnum {

    //****************************************
    //****** 通用 状态码************
    //****************************************

    /**
     * 成功
     */
    SUCCESS(1000, "操作成功"),

    /**
     * Token（已经过期、用户没登录），请先登录
     */
    TOKEN_EXPIRE(1101, "登录已过期，请重新登录"),

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
     * 流量异常，你已经被加入黑名单
     */
    BLACK_LIST(1211, "流量异常，你已经被加入黑名单"),

    /**
     * ip获取异常，无法获取客户端ip
     */
    IP_ACQUISITION_EXCEPTION(1212, "ip获取异常，无法获取客户端ip"),

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
     * 数据解析异常
     */
    AES_DECRYPT_ERROR(9994, "数据解析异常"),

    /**
     * 参数未配置
     */
    PARAM_NOT_EXIST(9993, "参数未配置"),

    /**
     * 服务暂时不可用
     */
    SERVER_UNAVAILABLE(9992, "服务暂时不可用"),

    /**
     * 请求的资源不存在
     */
    NOT_FOUND(9991, "请求的资源不存在"),

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
    MAN_SERVICE_PLATFORM_DEPLOY_YAPI_REQUEST_ERROR(49152, "请求Yapi服务失败"),
    MAN_SERVICE_PLATFORM_DEPLOY_YAPI_REQUEST_ERROR2(49153, "文件流输出异常"),

    ;


    private final int code;
    private final String message;

    ResponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(int code) {
        ResponseCodeEnum responseCodeEnum = Arrays.stream(ResponseCodeEnum.values()).filter(r -> r.getCode() == code).findFirst().orElse(null);
        if (responseCodeEnum != null) {
            return responseCodeEnum.getMessage();
        }
        return null;
    }

    public static String getMessage(int code, String msg) {
        ResponseCodeEnum responseCodeEnum = Arrays.stream(ResponseCodeEnum.values()).filter(r -> r.getCode() == code).findFirst().orElse(null);
        if (responseCodeEnum != null) {
            if (responseCodeEnum.getMessage().contains("%s") && !responseCodeEnum.getMessage().equals(msg)) {
                return String.format(responseCodeEnum.getMessage(), msg);
            }
            return CharSequenceUtil.isNotBlank(msg) ? msg : responseCodeEnum.getMessage();
        }
        return null;
    }

    public static ResponseCodeEnum getByCode(int code) {
        return Arrays.stream(ResponseCodeEnum.values()).filter(r -> r.getCode() == code).findFirst().orElse(BUSINESS_ERROR);
    }

}

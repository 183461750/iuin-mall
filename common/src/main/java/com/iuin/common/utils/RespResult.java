package com.iuin.common.utils;

import com.iuin.common.enums.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fa
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespResult<T> {

    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;

    /**
     * 状态码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应时间戳
     */
    private Long time;

    public static <T> RespResult<T> success() {
        ResponseCodeEnum responseCodeEnum = ResponseCodeEnum.SUCCESS;
        return new RespResult<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage(), null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> success(T data) {
        ResponseCodeEnum responseCodeEnum = ResponseCodeEnum.SUCCESS;
        return new RespResult<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage(), data, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(ResponseCodeEnum responseCodeEnum) {
        return new RespResult<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage(), null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> failService(String errorMessage) {
        return new RespResult<>(ResponseCodeEnum.SERVICE_ERROR.getCode(), errorMessage, null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> failFeignService(String errorMessage) {
        return new RespResult<>(ResponseCodeEnum.FEIGN_SERVICE_ERROR.getCode(), errorMessage, null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(String errorMessage) {
        return new RespResult<>(ResponseCodeEnum.BUSINESS_ERROR.getCode(), errorMessage, null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(ResponseCodeEnum responseCodeEnum, String errorMessage) {
        return new RespResult<>(responseCodeEnum.getCode(), errorMessage, null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(int responseCode, String errorMessage) {
        return new RespResult<>(responseCode, errorMessage, null, System.currentTimeMillis());
    }

}

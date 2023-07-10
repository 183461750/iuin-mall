package com.iuin.common.utils;

import cn.hutool.core.convert.Convert;
import com.iuin.common.utils.resp.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

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
        ResponseCode responseCode = ResponseCode.SUCCESS;
        return new RespResult<>(responseCode.getCode(), responseCode.getMessage(), null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> success(T data) {
        ResponseCode responseCode = ResponseCode.SUCCESS;
        return new RespResult<>(responseCode.getCode(), responseCode.getMessage(), data, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(ResponseCode responseCode) {
        return new RespResult<>(responseCode.getCode(), responseCode.getMessage(), null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> failService(String errorMessage) {
        return new RespResult<>(ResponseCode.SERVICE_ERROR.getCode(), errorMessage, null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> failFeignService(String errorMessage) {
        return new RespResult<>(ResponseCode.FEIGN_SERVICE_ERROR.getCode(), errorMessage, null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(String errorMessage) {
        return new RespResult<>(ResponseCode.BUSINESS_ERROR.getCode(), errorMessage, null, System.currentTimeMillis());
    }

    public static <T> RespResult<T> fail(ResponseCode responseCode, String errorMessage) {
        return new RespResult<>(responseCode.getCode(), errorMessage, null, System.currentTimeMillis());
    }

}

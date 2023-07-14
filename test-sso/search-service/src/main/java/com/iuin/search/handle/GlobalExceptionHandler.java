package com.iuin.search.handle;

import com.iuin.common.utils.RespResult;
import com.iuin.common.utils.resp.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 默认全局异常处理类，在所有异常处理类的最后
 *
 * @author Fa
 */
@RestControllerAdvice
@Order(100)
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public RespResult<?> validationBodyException(HttpServletRequest request, Exception exception) {
        exception.printStackTrace();
        //请求路径 + 错误信息
        log.error(exception.getMessage() == null ? request.getRequestURI().concat(" : ").concat(exception.getClass().getName()) : request.getRequestURI().concat(" : ").concat(exception.getMessage()), exception);
        return RespResult.fail(ResponseCode.BUSINESS_ERROR);
    }

}

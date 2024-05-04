package com.iuin.search.handle.exception;

import com.iuin.common.utils.RespResult;
import com.iuin.common.enums.ResponseCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

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
    public RespResult<Void> validationBodyException(HttpServletRequest request, Exception exception) {
        //请求路径 + 错误信息
        log.error(request.getRequestURI().concat(" : ").concat(Optional.ofNullable(exception.getMessage()).orElseGet(() -> exception.getClass().getName())), exception);
        return RespResult.fail(ResponseCodeEnum.BUSINESS_ERROR);
    }

}

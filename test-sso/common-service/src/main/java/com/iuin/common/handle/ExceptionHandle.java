package com.iuin.common.handle;

import cn.hutool.core.util.StrUtil;
import com.iuin.common.utils.RespResult;
import com.iuin.common.utils.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author fa
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandle {

    /**
     * 在Http Get查询接口使用@Valid时，不会直接获得VO中注解的message。使用下面的方法可以获得自定义的错误message
     *
     * @param exception BindException
     * @return 返回错误信息
     */
    @ExceptionHandler(BindException.class)
    public RespResult<?> validationBindException(BindException exception) {
        String errMsg = !CollectionUtils.isEmpty(exception.getAllErrors()) ? exception.getAllErrors().get(0).getDefaultMessage() : exception.getMessage();
        log.warn(errMsg);
        return RespResult.fail(ResponseCode.REQUEST_PARAM_CHECK_FAILED, errMsg);
    }

    /**
     * 校验错误拦截处理
     *
     * @param exception MethodArgumentNotValidException错误类
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespResult<?> validationBodyException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        //返回第一个错误
        String errMsg = result.getAllErrors().get(0).getDefaultMessage();
        log.warn(errMsg);
        return RespResult.fail(ResponseCode.REQUEST_PARAM_CHECK_FAILED, errMsg);
    }

    @ExceptionHandler(Exception.class)
    public Object exception(Exception exception) {
        StackTraceElement[] stackTraceElements = exception.getStackTrace();
        for (int i = 0; i < stackTraceElements.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            if (!StrUtil.contains(stackTraceElement.getClassName(), "example")) {
                stackTraceElements[i] = null;
            }
        }
        log.error(exception.getLocalizedMessage(), exception);
        return RespResult.fail(exception.getMessage());
    }

}

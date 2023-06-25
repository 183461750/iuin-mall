package com.iuin.search.handle;

import com.iuin.common.utils.RespResult;
import com.iuin.common.utils.resp.ResponseCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求参数校验异常处理类
 *
 * @author Fa
 */
@RestControllerAdvice
@Order(1)
public class ParamCheckExceptionHandler {
    /**
     * 校验错误拦截处理
     *
     * @param exception MethodArgumentNotValidException错误类
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespResult<?> validationBodyException(HttpServletRequest request, MethodArgumentNotValidException exception) {

        BindingResult result = exception.getBindingResult();

        String message = result.getAllErrors().get(0).getDefaultMessage();

        return RespResult.fail(ResponseCode.REQUEST_PARAM_CHECK_FAILED, message);
    }

    /**
     * 参数类型转换错误拦截处理
     *
     * @param exception HttpMessageConversionException错误类
     * @return 错误信息
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public RespResult<?> parameterTypeException(HttpMessageConversionException exception) {
        return RespResult.fail(ResponseCode.REQUEST_PARAM_CHECK_FAILED, exception.getMessage());
    }


    /**
     * Http请求方法不正确的异常
     * <p>例如：http post接口接收了http get请求</p>
     *
     * @param exception HttpRequestMethodNotSupportedException
     * @return 返回错误信息
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RespResult<?> requestMethodsException(HttpRequestMethodNotSupportedException exception) {
        return RespResult.fail(ResponseCode.REQUEST_METHOD_NOT_SUPPORTED, ResponseCode.REQUEST_METHOD_NOT_SUPPORTED.getMessage());
    }

    /**
     * 前端发送Http Post Json请求时，没有传递接口所需的参数
     *
     * @param exception HttpMessageNotReadableException
     * @return 返回错误信息
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RespResult<?> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return RespResult.fail(ResponseCode.REQUEST_BODY_IS_MISSING, ResponseCode.REQUEST_BODY_IS_MISSING.getMessage());
    }

    /**
     * Http请求头部的Content-type设置不正确
     *
     * @param exception HttpMediaTypeNotSupportedException
     * @return 返回错误信息
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public RespResult<?> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
        return RespResult.fail(ResponseCode.REQUEST_CONTENT_TYPE_NOT_SUPPORTED, ResponseCode.REQUEST_CONTENT_TYPE_NOT_SUPPORTED.getMessage());
    }

    /**
     * 在Http Get查询接口使用@Valid时，不会直接获得VO中注解的message。使用下面的方法可以获得自定义的错误message
     *
     * @param exception BindException
     * @return 返回错误信息
     */
    @ExceptionHandler(BindException.class)
    public RespResult<?> validationBindException(BindException exception) {
        String errMsg = !CollectionUtils.isEmpty(exception.getAllErrors()) ? exception.getAllErrors().get(0).getDefaultMessage() : exception.getMessage();
        return RespResult.fail(ResponseCode.REQUEST_PARAM_CHECK_FAILED, errMsg);
    }
}

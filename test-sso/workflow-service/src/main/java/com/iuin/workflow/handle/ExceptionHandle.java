package com.iuin.workflow.handle;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author fa
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandle {

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
        return exception.getMessage();
    }

}

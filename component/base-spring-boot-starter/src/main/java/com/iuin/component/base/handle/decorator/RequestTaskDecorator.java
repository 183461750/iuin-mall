package com.iuin.component.base.handle.decorator;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskDecorator;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Locale;

/**
 * 传递请求以及请求头等参数
 *
 * @author fa
 */
public class RequestTaskDecorator implements TaskDecorator {

    @Override
    @NonNull
    public Runnable decorate(@NonNull Runnable runnable) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Locale locale = LocaleContextHolder.getLocale();
        return () -> run(runnable, requestAttributes, locale);
    }

    /**
     * 运行
     */
    private static void run(Runnable runnable, RequestAttributes requestAttributes, Locale locale) {
        RequestContextHolder.setRequestAttributes(requestAttributes);
        LocaleContextHolder.setLocale(locale);
        try {
            runnable.run();
        } finally {
            RequestContextHolder.resetRequestAttributes();
            LocaleContextHolder.resetLocaleContext();
        }
    }

}

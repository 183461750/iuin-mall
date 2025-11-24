package com.iuin.mall.component.nacos.interceptors;

import cn.hutool.http.Header;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.Objects;

/**
 * feign请求头拦截器
 *
 * @author fa
 */
@Configuration
public class FeignReqHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        //将上一个服务接收到的部分请求头参数，传递到另一个服务中
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            return;
        }

        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (Objects.isNull(headerNames)) {
            return;
        }

        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            // 浏览器端传递的语言参数(用于国际化)
            if (Header.ACCEPT_LANGUAGE.getValue().equalsIgnoreCase(name)) {
                String values = request.getHeader(name);
                requestTemplate.header(name, values);
            }
        }

    }

}

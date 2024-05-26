package com.ssy.lingxi.component.nacos.interceptors;

import com.iuin.component.base.component.BaseServiceComponent;
import com.iuin.component.base.constants.ServiceHeaderConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 将version请求头通过feign传递到下游
 *
 * @author huan.fu
 * @since 2023/6/20 - 08:27
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VersionRequestInterceptor implements RequestInterceptor {

    private final BaseServiceComponent baseServiceComponent;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String version = baseServiceComponent.getHeaderNacosVersion();
        log.info("feign 中传递的 version 请求头的值为:[{}]", version);
        requestTemplate.header(ServiceHeaderConstant.HEADER_NACOS_VERSION, version);
    }
}

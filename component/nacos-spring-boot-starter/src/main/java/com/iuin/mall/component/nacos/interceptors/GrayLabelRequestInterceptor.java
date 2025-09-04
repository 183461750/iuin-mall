package com.iuin.mall.component.nacos.interceptors;

import com.iuin.mall.component.nacos.components.NacosHeaderComponent;
import com.iuin.mall.component.nacos.constant.NacosHeaderConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * 将gray-label请求头通过feign传递到下游
 *
 * @author Fa
 */
@Slf4j
public record GrayLabelRequestInterceptor(NacosHeaderComponent nacosHeaderComponent) implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String version = nacosHeaderComponent.getHeaderGrayLabel();
        log.info("[feign拦截器]: 请求中传递的 version 头的值为:[{}]", version);
        requestTemplate.header(NacosHeaderConstant.HEADER_GRAY_LABEL, version);
    }

}

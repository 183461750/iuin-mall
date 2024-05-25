package com.iuin.component.base.component;

import cn.hutool.core.util.StrUtil;
import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.component.base.constants.ServiceHeaderConstant;
import com.iuin.component.base.exceptions.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * 基础服务组件
 *
 * @author fa
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BaseServiceComponent {

    /**
     * 获取请求对象
     */
    public HttpServletRequest getRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes()).map(ServletRequestAttributes.class::cast).map(ServletRequestAttributes::getRequest).orElseThrow(
                () -> new BusinessException(ResponseCodeEnum.REQUEST_OBJECT_LOST)
        );
    }

    /**
     * 获取请求头中的商城Id
     */
    public Long getHeaderShopId() {
        HttpServletRequest request = getRequest();

        String headerValue = request.getHeader(ServiceHeaderConstant.HEADER_SHOP_ID);

        return Optional.ofNullable(headerValue).filter(StrUtil::isNotBlank).map(Long::parseLong).orElseThrow(
                () -> new BusinessException(ResponseCodeEnum.HEADER_SHOP_ID_CAN_NOT_BE_EMPTY)
        );
    }

    /**
     * 获取请求头中的nacos版本
     */
    public String getHeaderNacosVersion() {
        HttpServletRequest request = getRequest();

        String headerValue = request.getHeader(ServiceHeaderConstant.HEADER_NACOS_VERSION);

        return Optional.ofNullable(headerValue).filter(StrUtil::isNotBlank).orElseThrow(
                () -> new BusinessException(ResponseCodeEnum.HEADER_SHOP_ID_CAN_NOT_BE_EMPTY)
        );
    }

    /**
     * 获取请求头中的nacos分组
     */
    public String getHeaderNacosGroup() {
        HttpServletRequest request = getRequest();

        String headerValue = request.getHeader(ServiceHeaderConstant.HEADER_NACOS_GROUP);

        return Optional.ofNullable(headerValue).filter(StrUtil::isNotBlank).orElseThrow(
                () -> new BusinessException(ResponseCodeEnum.HEADER_SHOP_ID_CAN_NOT_BE_EMPTY)
        );
    }

}

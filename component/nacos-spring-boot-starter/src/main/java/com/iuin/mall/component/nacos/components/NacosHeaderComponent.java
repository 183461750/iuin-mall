package com.iuin.mall.component.nacos.components;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.common.model.exceptions.BusinessException;
import com.iuin.mall.component.nacos.constant.NacosHeaderConstant;
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
public class NacosHeaderComponent {

    /**
     * 获取请求对象
     */
    public HttpServletRequest getRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes()).map(ServletRequestAttributes.class::cast).map(ServletRequestAttributes::getRequest).orElseThrow(
                () -> new BusinessException(ResponseCodeEnum.REQUEST_OBJECT_LOST)
        );
    }

    /**
     * 获取请求头中的灰度标签
     */
    public String getHeaderGrayLabel() {
        return this.getHeaderGrayLabel(false);
    }

    /**
     * 获取请求头中的灰度标签
     */
    public String getHeaderGrayLabel(Boolean isThrow) {
        HttpServletRequest request = getRequest();

        String headerValue = request.getHeader(NacosHeaderConstant.HEADER_GRAY_LABEL);

        if (Boolean.FALSE.equals(isThrow)) {
            return headerValue;
        }

        return Optional.ofNullable(headerValue).filter(StrUtil::isNotBlank).orElseThrow(
                () -> new BusinessException(ResponseCodeEnum.HEADER_PARAM_CAN_NOT_BE_EMPTY, CharSequenceUtil.format(
                        ResponseCodeEnum.HEADER_PARAM_CAN_NOT_BE_EMPTY.getMessage(), NacosHeaderConstant.HEADER_GRAY_LABEL
                ))
        );
    }

    /**
     * 获取请求头中的nacos分组
     */
    public String getHeaderNacosGroup() {
        return this.getHeaderNacosGroup(false);
    }

    /**
     * 获取请求头中的nacos分组
     */
    public String getHeaderNacosGroup(Boolean isThrow) {
        HttpServletRequest request = getRequest();

        String headerValue = request.getHeader(NacosHeaderConstant.HEADER_NACOS_GROUP);

        if (Boolean.FALSE.equals(isThrow)) {
            return headerValue;
        }

        return Optional.ofNullable(headerValue).filter(StrUtil::isNotBlank).orElseThrow(
                () -> new BusinessException(ResponseCodeEnum.HEADER_PARAM_CAN_NOT_BE_EMPTY, CharSequenceUtil.format(
                        ResponseCodeEnum.HEADER_PARAM_CAN_NOT_BE_EMPTY.getMessage(), NacosHeaderConstant.HEADER_NACOS_GROUP
                ))
        );
    }

}

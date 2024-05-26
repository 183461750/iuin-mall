package com.iuin.search.controller.feign;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.iuin.common.utils.RespResult;
import com.iuin.search.api.feign.ProviderFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 提供者控制器
 *
 * @author huan.fu
 * @since 2023/3/6 - 21:58
 */
@RestController
@RequiredArgsConstructor
public class ProviderFeignController implements ProviderFeign {

    private final NacosDiscoveryProperties nacosDiscoveryProperties;

    /**
     * 获取服务信息
     *
     * @return ip:port
     */
    @Override
    public RespResult<String> fetchServerInfo() {
        return RespResult.success(nacosDiscoveryProperties.getIp() + ":" + nacosDiscoveryProperties.getPort());
    }
}

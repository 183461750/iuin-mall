package com.iuin.commodity.controller;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.iuin.common.constants.ModuleConstant;
import com.iuin.common.utils.RespUtil;
import com.iuin.search.api.feign.ProviderFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 消费者控制器
 *
 * @author huan.fu
 * @since 2023/6/19 - 22:21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ModuleConstant.COMMODITY_PATH_PREFIX + "/consumer")
public class ConsumerController {

    private final ProviderFeign providerFeign;
    private final NacosDiscoveryProperties nacosDiscoveryProperties;

    @GetMapping("/fetchProviderServerInfo")
    public Map<String, String> fetchProviderServerInfo() {
        Map<String, String> ret = new HashMap<>(4);
        ret.put("consumer信息", nacosDiscoveryProperties.getIp() + ":" + nacosDiscoveryProperties.getPort());
        ret.put("provider信息", RespUtil.getData(providerFeign.fetchServerInfo()));
        return ret;
    }
}

package com.iuin.config.controller;

import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.NacosConfigService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URL;
import java.util.List;

/**
 * @author fa
 */
@RestController
@RequestMapping("/nacos/config")
public class NacosConfigController {

    /**
     * todo 待完成
     */
    private NacosConfigService nacosConfigService;

    /**
     * 更新服务配置文件
     */
    @PostMapping(value = "/update")
    public Object update() throws NacosException {
        List<URL> urlList = ResourceUtil.getResources("");
        return SaResult.data(nacosConfigService.publishConfig("public", "DEFAULT_GROUP", "content"));
    }

}

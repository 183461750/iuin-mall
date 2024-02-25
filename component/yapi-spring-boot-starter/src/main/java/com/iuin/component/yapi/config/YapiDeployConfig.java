package com.iuin.component.yapi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Configuration
@RefreshScope
@Data
public class YapiDeployConfig {

    //yapi访问信息
    @Value("${yapi.serverUrl}")
    private String serverUrl;

    @Value("${yapi.email}")
    private String email;

    @Value("${yapi.password}")
    private String password = "ssy123456";

    @Value("${yapi.groupId}")
    private String groupId;

    public List<String> getConfigList() {
        return Arrays.asList(serverUrl, email, password, groupId);
    }
}

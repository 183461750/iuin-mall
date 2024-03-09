package com.iuin.component.yapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Arrays;
import java.util.List;

/**
 * yapi访问信息
 *
 * @author fa
 */
@Data
@ConfigurationProperties("yapi")
public class YapiDeployConfig {

    private String serverUrl;

    private String email;

    private String password;

    private String groupId;

    public List<String> getConfigList() {
        return Arrays.asList(serverUrl, email, password, groupId);
    }
}

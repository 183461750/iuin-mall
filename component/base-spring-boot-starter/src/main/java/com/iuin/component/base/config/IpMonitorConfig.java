package com.iuin.component.base.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * ip监控配置类
 *
 * @author Fa
 */
@Data
@RefreshScope
@Configuration
public class IpMonitorConfig {
    /**
     * 是否开启ip监控
     */
    @Value("${security.enableIpMonitor:true}")
    private Boolean enableIpMonitor;
}

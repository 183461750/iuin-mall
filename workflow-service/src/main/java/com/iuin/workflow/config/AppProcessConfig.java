package com.iuin.workflow.config;

import com.iuin.workflow.model.dto.ProcessDefListDTO;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 流程配置
 *
 * @author Fa
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app.process.config")
public class AppProcessConfig {

    private ProcessDefListDTO processDefList;

}

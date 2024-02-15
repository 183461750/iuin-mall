package com.iuin.component.pluggable_annotation.version.config;

import com.iuin.component.pluggable_annotation.version.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fa
 */
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(VersionProperties.class)
public class VersionAutoConfiguration {

    private final VersionProperties versionProperties;

    @Bean
    @ConditionalOnMissingBean
    public VersionService versionService() {
        return new VersionService(versionProperties);
    }

}

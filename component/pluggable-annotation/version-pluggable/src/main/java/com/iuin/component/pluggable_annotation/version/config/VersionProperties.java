package com.iuin.component.pluggable_annotation.version.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fa
 */
@Data
@ConfigurationProperties("iuin.version")
public class VersionProperties {

    private  String version;

}

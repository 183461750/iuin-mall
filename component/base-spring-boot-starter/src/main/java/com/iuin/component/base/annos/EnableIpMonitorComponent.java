package com.iuin.component.base.annos;

import com.iuin.component.base.config.IpMonitorConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fa
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({IpMonitorConfig.class})
public @interface EnableIpMonitorComponent {

}

package com.iuin.component.base.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author fa
 */
//@ComponentScan(basePackageClasses = EnableBaseComponent.class)
@ComponentScan(basePackages = "com.iuin.component.base.handle")
//@Import({
////        ValidationConfig.class,
//        IpMonitorConfig.class,
//})
public class BaseAutoConfig {

}

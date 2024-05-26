package com.iuin.component.base.config;

import org.springframework.context.annotation.ComponentScan;

/**
 * @author fa
 */
@ComponentScan({
//        "com.iuin.component.base.handle",
//        "com.iuin.component.base.component",
        // 需要配合注入注解一起使用{@link Configuration @Configuration}, 所以扫描父包即可
        "com.iuin.component.base",
})
public class BaseAutoConfig {

}

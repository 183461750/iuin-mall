package com.iuin.component.base.annos;

import com.iuin.component.base.config.BaseAutoConfig;
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
@Import({BaseAutoConfig.class})
public @interface EnableBaseComponent {

}

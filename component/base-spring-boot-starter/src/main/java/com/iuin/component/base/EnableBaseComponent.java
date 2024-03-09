package com.iuin.component.base;

import com.iuin.component.base.config.ValidationConfig;
import com.iuin.component.base.handle.ExceptionHandle;
import com.iuin.component.base.holders.EnvHolder;
import org.springframework.context.annotation.ComponentScan;
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
@Import({ValidationConfig.class})
@ComponentScan(basePackageClasses = EnableBaseComponent.class)
public @interface EnableBaseComponent {

}

package com.iuin.common;

import com.iuin.common.config.ValidationConfig;
import com.iuin.common.handle.ExceptionHandle;
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
@Import({
        ValidationConfig.class,
        ExceptionHandle.class
})
public @interface EnableCommonService {

}

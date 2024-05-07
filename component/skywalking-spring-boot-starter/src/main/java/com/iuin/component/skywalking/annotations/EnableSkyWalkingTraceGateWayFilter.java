package com.iuin.component.skywalking.annotations;

import com.iuin.component.skywalking.filters.SkyWalkingTraceGatewayFilter;
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
@Import({SkyWalkingTraceGatewayFilter.class})
public @interface EnableSkyWalkingTraceGateWayFilter {

}

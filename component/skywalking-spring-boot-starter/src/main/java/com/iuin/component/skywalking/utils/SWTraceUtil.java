package com.iuin.component.skywalking.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;
import org.springframework.web.server.adapter.DefaultServerWebExchange;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 获取traceId的工具类
 */
public class SWTraceUtil {

    private static final Logger log = LoggerFactory.getLogger(SWTraceUtil.class);
    private static Method GET_SW_dynamic_field = null;
    private static Method GET_SW_TRACE_ID = null;
    private static Method GET_ID = null;
    private static boolean IS_METHOD_INIT = false;

    public static String getTraceId(ServerWebExchange exchange) {
      return  getTraceIdInternal(exchange);

    }

    static String initMethodAndGetTraceId(Object enhancedInstance) {
        String traceId = null;
        try {
            GET_SW_dynamic_field = enhancedInstance.getClass().getDeclaredMethod("getSkyWalkingDynamicField");
            Object contextSnapshot = GET_SW_dynamic_field.invoke(enhancedInstance);
            if (contextSnapshot != null) {
                GET_SW_TRACE_ID = contextSnapshot.getClass().getDeclaredMethod("getTraceId");
                Object distributedTraceId = GET_SW_TRACE_ID.invoke(contextSnapshot);
                if (distributedTraceId != null) {
                    GET_ID = distributedTraceId.getClass().getMethod("getId");
                    traceId = (String) GET_ID.invoke(distributedTraceId);
                    IS_METHOD_INIT = true;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("initMethodAndGetTraceId happened exception: " + e);
            IS_METHOD_INIT = false;
        }
        return traceId;

    }

    private static String getTraceIdInternal(ServerWebExchange exchange) {
        Object enhancedInstance = getEnhancedInstance(exchange);
        if (enhancedInstance != null) {
            if (!IS_METHOD_INIT) {
                synchronized (SWTraceUtil.class) {
                    if (!IS_METHOD_INIT) {
                        return initMethodAndGetTraceId(enhancedInstance);
                    }
                }
            }
            return getTraceIdWithInited(enhancedInstance);
        }
        return null;
    }


    private static String getTraceIdWithInited(Object enhancedInstance) {
        try {
            Object contextSnapshot = GET_SW_dynamic_field.invoke(enhancedInstance);
            if (contextSnapshot != null) {
                Object distributedTraceId = GET_SW_TRACE_ID.invoke(contextSnapshot);
                if (distributedTraceId != null) {
                    return (String) GET_ID.invoke(distributedTraceId);
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("getTraceIdWithInstance happened exception:" + e);
        }
        return null;
    }

    private static Object getEnhancedInstance(Object object) {
        Object enhancedInstance = null;
        if (object != null) {
            if (object instanceof DefaultServerWebExchange) {
                enhancedInstance = object;
            } else if (object instanceof ServerWebExchangeDecorator) {
                ServerWebExchange delegate = ((ServerWebExchangeDecorator) object).getDelegate();
                return getEnhancedInstance(delegate);
            }
        }
        return enhancedInstance;

    }
}


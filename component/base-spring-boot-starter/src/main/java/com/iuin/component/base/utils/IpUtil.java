package com.iuin.component.base.utils;

import cn.hutool.core.text.CharSequenceUtil;
import com.iuin.common.enums.ResponseCodeEnum;
import com.iuin.common.model.exceptions.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * ip工具类
 *
 * @author jw.chen
 * @version 3.0.0
 * @since 2024/1/11
 */
@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class IpUtil {
    private static final String IP_UTILS_FLAG = ",";
    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
    private static final String LOCALHOST_IPV4 = "127.0.0.1";

    /**
     * 获取IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip;
        try {
            // 以下两个获取在k8s中，将真实的客户端IP，放到了x-Original-Forwarded-For。而将WAF的回源地址放到了 x-Forwarded-For了。
            ip = request.getHeader("X-Original-Forwarded-For");
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }

            // 获取nginx等代理的ip
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("x-forwarded-for");
            }
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }

            // 兼容k8s集群获取ip
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (LOCALHOST_IPV4.equalsIgnoreCase(ip) || LOCALHOST_IPV6.equalsIgnoreCase(ip)) {
                    // 根据网卡取本机配置的IP
                    ip = InetAddress.getLocalHost().getHostAddress();
                }
            }
        } catch (Exception e) {
            log.error("IPUtil ERROR: {0}", e);
            throw new BusinessException(ResponseCodeEnum.IP_ACQUISITION_EXCEPTION);
        }

        if (CharSequenceUtil.isEmpty(ip)) {
            throw new BusinessException(ResponseCodeEnum.IP_ACQUISITION_EXCEPTION);
        }

        // 使用代理，则获取第一个IP地址
        if (ip.contains(IP_UTILS_FLAG)) {
            ip = ip.substring(0, ip.indexOf(IP_UTILS_FLAG));
        }

        return LOCALHOST_IPV6.equalsIgnoreCase(ip) ? LOCALHOST_IPV4 : ip;
    }

    /**
     * 获取IP地址
     */
    public static String getIpAddr(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip;
        try {
            // 以下两个获取在k8s中，将真实的客户端IP，放到了x-Original-Forwarded-For。而将WAF的回源地址放到了 x-Forwarded-For了。
            ip = headers.getFirst("X-Original-Forwarded-For");
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = headers.getFirst("X-Forwarded-For");
            }

            // 获取nginx等代理的ip
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = headers.getFirst("x-forwarded-for");
            }
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = headers.getFirst("Proxy-Client-IP");
            }
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = headers.getFirst("WL-Proxy-Client-IP");
            }
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = headers.getFirst("HTTP_CLIENT_IP");
            }
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
            }

            // 兼容k8s集群获取ip
            if (CharSequenceUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
                InetSocketAddress remoteAddress = request.getRemoteAddress();
                if (Objects.nonNull(remoteAddress)) {
                    ip = remoteAddress.getAddress().getHostAddress();
                    if (LOCALHOST_IPV4.equalsIgnoreCase(ip) || LOCALHOST_IPV6.equalsIgnoreCase(ip)) {
                        // 根据网卡取本机配置的IP
                        ip = InetAddress.getLocalHost().getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            log.error("IPUtil ERROR: {0}", e);
            throw new BusinessException(ResponseCodeEnum.IP_ACQUISITION_EXCEPTION);
        }

        if (CharSequenceUtil.isEmpty(ip)) {
            throw new BusinessException(ResponseCodeEnum.IP_ACQUISITION_EXCEPTION);
        }

        // 使用代理，则获取第一个IP地址
        if (ip.contains(IP_UTILS_FLAG)) {
            ip = ip.substring(0, ip.indexOf(IP_UTILS_FLAG));
        }

        return LOCALHOST_IPV6.equalsIgnoreCase(ip) ? LOCALHOST_IPV4 : ip;
    }

}

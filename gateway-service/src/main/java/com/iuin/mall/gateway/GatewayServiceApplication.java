package com.iuin.mall.gateway;

import com.iuin.component.base.annos.EnableIpMonitorComponent;
import com.iuin.component.skywalking.annotations.EnableSkyWalkingTraceGateWayFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Fa
 */
@EnableIpMonitorComponent
@EnableSkyWalkingTraceGateWayFilter
@SpringBootApplication
public class GatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}

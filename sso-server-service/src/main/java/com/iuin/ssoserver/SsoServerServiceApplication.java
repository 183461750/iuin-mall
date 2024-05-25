package com.iuin.ssoserver;

import cn.dev33.satoken.SaManager;
import com.iuin.component.base.annos.EnableBaseComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author fa
 */
@EnableJpaAuditing
//@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.iuin.**.api.feign"})
@EnableBaseComponent
public class SsoServerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoServerServiceApplication.class, args);
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }

}

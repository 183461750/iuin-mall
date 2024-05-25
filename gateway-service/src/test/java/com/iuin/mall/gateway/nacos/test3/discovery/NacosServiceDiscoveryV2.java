package com.iuin.mall.gateway.nacos.test3.discovery;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public class NacosServiceDiscoveryV2 extends NacosServiceDiscovery {

    private final NacosDiscoveryProperties discoveryProperties;
    private final NacosServiceManager nacosServiceManager;

    public NacosServiceDiscoveryV2(NacosDiscoveryProperties discoveryProperties, NacosServiceManager nacosServiceManager) {
        super(discoveryProperties, nacosServiceManager);
        this.discoveryProperties = discoveryProperties;
        this.nacosServiceManager = nacosServiceManager;
    }

    // 重写该方法
    public List<ServiceInstance> getInstances(String serviceId) throws NacosException {
        String group = this.discoveryProperties.getGroup();
        // 优先保证同分组下的服务调用
        List<Instance> instances = this.namingService().selectInstances(serviceId, group, true);
        if (CollUtil.isEmpty(instances)) {
            // 如果同分组下找不到服务,那么就从默认分组下找服务
            instances = this.namingService().selectInstances(serviceId, "DEFAULT_GROUP", true);
        }
        return hostToServiceInstanceList(instances, serviceId);
    }

    private NamingService namingService() {
        return this.nacosServiceManager.getNamingService();
    }

}

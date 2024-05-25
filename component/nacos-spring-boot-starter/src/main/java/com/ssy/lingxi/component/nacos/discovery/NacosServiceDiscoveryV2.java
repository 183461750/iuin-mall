package com.ssy.lingxi.component.nacos.discovery;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.iuin.component.base.component.BaseServiceComponent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.Optional;

/**
 * nacos服务发现-跨分组<br/>
 * 可用于隔离本地和线上环境
 *
 * @author fa
 */
public class NacosServiceDiscoveryV2 extends NacosServiceDiscovery {

    private final NacosDiscoveryProperties discoveryProperties;
    private final NacosServiceManager nacosServiceManager;
    private final BaseServiceComponent baseServiceComponent;

    public NacosServiceDiscoveryV2(NacosDiscoveryProperties discoveryProperties, NacosServiceManager nacosServiceManager, BaseServiceComponent baseServiceComponent) {
        super(discoveryProperties, nacosServiceManager);
        this.discoveryProperties = discoveryProperties;
        this.nacosServiceManager = nacosServiceManager;
        this.baseServiceComponent = baseServiceComponent;
    }

    /**
     * 获取服务实例
     */
    @Override
    public List<ServiceInstance> getInstances(String serviceId) throws NacosException {
        String group = this.discoveryProperties.getGroup();
        // 优先保证同分组下的服务调用
        List<Instance> instances = this.namingService().selectInstances(serviceId, group, true);
        if (CollUtil.isEmpty(instances)) {
            // 如果同分组下找不到服务,那么就从默认分组下找服务
            String defaultGroup = Optional.ofNullable(baseServiceComponent.getHeaderNacosGroup()).orElse("DEFAULT_GROUP");
            instances = this.namingService().selectInstances(serviceId, defaultGroup, true);
        }
        return hostToServiceInstanceList(instances, serviceId);
    }

    /**
     * 获取NamingService
     */
    @SneakyThrows
    private NamingService namingService() {
//        return this.nacosServiceManager.getNamingService();
        return new NamingServiceV2(discoveryProperties.getNacosProperties(), baseServiceComponent);
    }

}

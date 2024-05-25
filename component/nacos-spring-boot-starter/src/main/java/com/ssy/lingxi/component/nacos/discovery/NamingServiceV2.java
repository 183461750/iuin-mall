package com.ssy.lingxi.component.nacos.discovery;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ServiceInfo;
import com.alibaba.nacos.client.env.NacosClientProperties;
import com.alibaba.nacos.client.naming.NacosNamingService;
import com.alibaba.nacos.client.naming.cache.ServiceInfoHolder;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;
import com.alibaba.nacos.client.naming.event.InstancesChangeNotifier;
import com.alibaba.nacos.client.naming.remote.NamingClientProxy;
import com.alibaba.nacos.client.naming.remote.NamingClientProxyDelegate;
import com.alibaba.nacos.client.naming.utils.InitUtils;
import com.alibaba.nacos.client.utils.PreInitUtils;
import com.alibaba.nacos.client.utils.ValidatorUtils;
import com.alibaba.nacos.common.notify.NotifyCenter;
import com.alibaba.nacos.common.utils.JacksonUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.iuin.component.base.component.BaseServiceComponent;
import com.iuin.component.base.constants.ServiceHeaderConstant;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

import static com.alibaba.nacos.client.utils.LogUtils.NAMING_LOGGER;

/**
 * @author fa
 */
public class NamingServiceV2 extends NacosNamingService {

    /**
     * Each Naming service should have different namespace.
     */
    private String namespace;

    private ServiceInfoHolder serviceInfoHolder;

    private InstancesChangeNotifier changeNotifier;

    private NamingClientProxy clientProxy;

    private String notifierEventScope;

    private BaseServiceComponent baseServiceComponent;

    public NamingServiceV2(Properties properties, BaseServiceComponent baseServiceComponent) throws NacosException {
        super(properties);
        init(properties);
        this.baseServiceComponent = baseServiceComponent;
    }

    @Override
    public Instance selectOneHealthyInstance(String serviceName, String groupName, List<String> clusters,
                                             boolean subscribe) throws NacosException {
        ServiceInfo serviceInfo = getServiceInfo(serviceName, groupName, clusters, subscribe);

        // 自定义通过请求头选择实例
        String nacosVersion = baseServiceComponent.getHeaderNacosVersion();
        for (Instance instance : serviceInfo.getHosts()) {
            if (Objects.equals(nacosVersion, instance.getMetadata().get(ServiceHeaderConstant.HEADER_NACOS_VERSION))) {
                return instance;
            }
        }

        // 默认按权重随机选择
        return Balancer.RandomByWeight.selectHost(serviceInfo);
    }

    private ServiceInfo getServiceInfo(String serviceName, String groupName, List<String> clusters, boolean subscribe)
            throws NacosException {
        ServiceInfo serviceInfo;
        String clusterString = StringUtils.join(clusters, ",");
        if (serviceInfoHolder.isFailoverSwitch()) {
            serviceInfo = getServiceInfoByFailover(serviceName, groupName, clusterString);
            if (serviceInfo != null && serviceInfo.getHosts().size() > 0) {
                NAMING_LOGGER.debug("getServiceInfo from failover,serviceName: {}  data:{}", serviceName,
                        JacksonUtils.toJson(serviceInfo.getHosts()));
                return serviceInfo;
            }
        }

        serviceInfo = getServiceInfoBySubscribe(serviceName, groupName, clusterString, subscribe);
        return serviceInfo;
    }

    private void init(Properties properties) throws NacosException {
        PreInitUtils.asyncPreLoadCostComponent();
        final NacosClientProperties nacosClientProperties = NacosClientProperties.PROTOTYPE.derive(properties);
        ValidatorUtils.checkInitParam(nacosClientProperties);
        this.namespace = InitUtils.initNamespaceForNaming(nacosClientProperties);
        InitUtils.initSerialization();
        InitUtils.initWebRootContext(nacosClientProperties);
//        initLogName(nacosClientProperties);

        this.notifierEventScope = UUID.randomUUID().toString();
        this.changeNotifier = new InstancesChangeNotifier(this.notifierEventScope);
        NotifyCenter.registerToPublisher(InstancesChangeEvent.class, 16384);
        NotifyCenter.registerSubscriber(changeNotifier);
        this.serviceInfoHolder = new ServiceInfoHolder(namespace, this.notifierEventScope, nacosClientProperties);
        this.clientProxy = new NamingClientProxyDelegate(this.namespace, serviceInfoHolder, nacosClientProperties,
                changeNotifier);
    }

    private ServiceInfo getServiceInfoByFailover(String serviceName, String groupName, String clusterString) {
        return serviceInfoHolder.getFailoverServiceInfo(serviceName, groupName, clusterString);
    }

    private ServiceInfo getServiceInfoBySubscribe(String serviceName, String groupName, String clusterString,
                                                  boolean subscribe) throws NacosException {
        ServiceInfo serviceInfo;
        if (subscribe) {
            serviceInfo = serviceInfoHolder.getServiceInfo(serviceName, groupName, clusterString);
            if (null == serviceInfo || !clientProxy.isSubscribed(serviceName, groupName, clusterString)) {
                serviceInfo = clientProxy.subscribe(serviceName, groupName, clusterString);
            }
        } else {
            serviceInfo = clientProxy.queryInstancesOfService(serviceName, groupName, clusterString, false);
        }
        return serviceInfo;
    }

}

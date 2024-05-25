package com.iuin.mall.gateway.nacos.test1.nacos.lbs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 灰度发布负载均衡策略
 */

@Slf4j
public class GrayLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private static final String VERSION = "version";
    private static final String LOCAL_VERSION = "local";
    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
    private String serviceId;


    public GrayLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        HttpHeaders headers = (HttpHeaders) request.getContext();

        if (this.serviceInstanceListSupplierProvider != null) {
            ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
            return supplier.get().next().map(item -> getInstanceResponse(item, headers));
        }
        return null;
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, HttpHeaders headers) {
        if (instances.isEmpty()) {
            return getServiceInstanceEmptyResponse();
        }
        return getServiceInstanceResponseByUidsOrGrayTag(instances, headers);
    }

    /**
     * 从nacos获取服务实例列表，并根据策略返回灰度服务的实例还是正常服务的实例
     */
    private Response<ServiceInstance> getServiceInstanceResponseByUidsOrGrayTag(List<ServiceInstance> instances, HttpHeaders headers) {
        List<ServiceInstance> grayInstances = new ArrayList<>();
        List<ServiceInstance> normalInstances = new ArrayList<>();

        for (ServiceInstance instance : instances) {
            Map<String, String> metadata = instance.getMetadata();
            // nacos元数据包含“gray-tag”的key值,且value="true"，则判定为灰度实例
            String isGrayInstance = metadata.get(VERSION);
            if (LOCAL_VERSION.equals(isGrayInstance)) {
                grayInstances.add(instance);
            } else {
                normalInstances.add(instance);
            }
        }
        //没有灰度服务，直接返回
        if (grayInstances.isEmpty()) {
            return new DefaultResponse(chooseOneInstance(normalInstances));
        }
        //有灰度服务，判断是否需要灰度
        if (checkIfNeedGray(headers)) {
            log.info("gray service of {} will be called", this.serviceId);
            return new DefaultResponse(chooseOneInstance(grayInstances));
        }
        return new DefaultResponse(chooseOneInstance(normalInstances));
    }

    /**
     * 从实例列表中获取其中一个实例的策略实现，这里采用的是随机挑选
     * pick strategy 可以根据业务需要，在这个方法里改写
     */
    private ServiceInstance chooseOneInstance(List<ServiceInstance> serviceInstances) {

        // strategy 1：可用的里面随机选择一个
        int size = serviceInstances.size();
        if (size == 1) {
            return serviceInstances.get(0);
        }
        Random rand = new Random();
        int random = rand.nextInt(size);
        return serviceInstances.get(random);
    }

    /**
     * 灰度判断逻辑：
     * 1. 判断请求header里是否用灰度标识的 kv，有则走灰度服务
     * 2. 如果 1 不满足，则判断请求的用户 id 是否在灰度用户池中，有则走灰度服务
     * 3. 1 和 2 都不满足，走正常服务
     */
    private boolean checkIfNeedGray(HttpHeaders headers) {
        String version = headers.getFirst(VERSION);
        if (version != null) {
            if (LOCAL_VERSION.equalsIgnoreCase(version)) {
                return true;
            }
        }
        return false;
    }

    private Response<ServiceInstance> getServiceInstanceEmptyResponse() {
        log.warn("No servers available for service: " + this.serviceId);
        return new EmptyResponse();
    }
}

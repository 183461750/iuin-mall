package com.iuin.mall.gateway.nacos.test2.nacos.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * @ClassName: GrayLoadBalancer
 * @Description TODO
 * @author 月夜烛峰
 * @date 2022/10/8 15:35
 */
@Slf4j
public class GrayLoadBalancer  {
 
    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
    private  String serviceId;
 
    public GrayLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }
 
 
    public Mono<Response<ServiceInstance>> choose(Request request) {
        HttpHeaders headers = (HttpHeaders) request.getContext();
        if (this.serviceInstanceListSupplierProvider != null) {
            ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
            return ((Flux)supplier.get()).next().map(list->getInstanceResponse((List<ServiceInstance>)list,headers));
        }
        return null;
    }
 
    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances,HttpHeaders headers) {
        if (instances.isEmpty()) {
            log.info("实例为空....");
            return getServiceInstanceEmptyResponse();
        } else {
            //return getServiceInstanceResponseWithWeight(instances);
            return getServiceInstanceResponseByVersion(instances, headers);
        }
    }
 
 
 
    /**
     * 根据版本进行分发
     * @param instances
     * @param headers
     * @return
     */
    private Response<ServiceInstance> getServiceInstanceResponseByVersion(List<ServiceInstance> instances, HttpHeaders headers) {
        String versionNo = headers.getFirst("version");
        log.info("查看版本:{}",versionNo);
        Map<String,String> versionMap = new HashMap<>(16);
        versionMap.put("version",versionNo);
        final Set<Map.Entry<String,String>> attributes =
                Collections.unmodifiableSet(versionMap.entrySet());
        ServiceInstance serviceInstance = null;
        for (ServiceInstance instance : instances) {
            log.info("开始获取实例instances...{}", JSONObject.toJSONString(instances));
            Map<String,String> metadata = instance.getMetadata();
            if(metadata.entrySet().containsAll(attributes)){
                log.info("开始获取实例...");
                serviceInstance = instance;
                break;
            }
        }
 
        if(ObjectUtils.isEmpty(serviceInstance)){
//            log.info("实例为空...");
//            return getServiceInstanceEmptyResponse();
            return getServiceInstanceResponseWithWeight(instances);
        }
        return new DefaultResponse(serviceInstance);
    }
 
//    /**
//     *
//     * 根据在nacos中配置的权重值，进行分发
//     * @param instances
//     *
//     * @return
//     */
//    private Response<ServiceInstance> getServiceInstanceResponseWithWeight(List<ServiceInstance> instances) {
//
//        Map<ServiceInstance,Integer> weightMap = new HashMap<>();
//        for (ServiceInstance instance : instances) {
//            log.info("获取所有实例:{}",JSONObject.toJSONString(instance));
//            Map<String,String> metadata = instance.getMetadata();
//            log.info("版本信息筛选: {} -->nacos.weight: {}",metadata.get("version"),metadata.get("nacos.weight"));
//            if(metadata.containsKey("nacos.weight")){
//                weightMap.put(instance,Integer.valueOf(metadata.get("nacos.weight")));
//            }
//        }
//        WeightMeta<ServiceInstance> weightMeta = WeightRandomUtils.buildWeightMeta(weightMap);
//        if(ObjectUtils.isEmpty(weightMeta)){
//            return getServiceInstanceEmptyResponse();
//        }
//        ServiceInstance serviceInstance = weightMeta.random();
//        if(ObjectUtils.isEmpty(serviceInstance)){
//            return getServiceInstanceEmptyResponse();
//        }
//        log.info(serviceInstance.getMetadata().get("version"));
//        return new DefaultResponse(serviceInstance);
//    }

    /**
     *
     * 根据在nacos中配置的权重值，进行分发
     * @param instances
     *
     * @return
     */
    private Response<ServiceInstance> getServiceInstanceResponseWithWeight(List<ServiceInstance> instances) {

        ArrayList<WeightRandom.WeightObj<ServiceInstance>> weightList = CollUtil.toList();
        for (ServiceInstance instance : instances) {
            log.info("获取所有实例:{}",JSONObject.toJSONString(instance));
            Map<String,String> metadata = instance.getMetadata();
            log.info("版本信息筛选: {} -->nacos.weight: {}",metadata.get("version"),metadata.get("nacos.weight"));
            if(metadata.containsKey("nacos.weight")){
                weightList.add(new WeightRandom.WeightObj<>(instance, Double.parseDouble(metadata.get("nacos.weight"))));
            }
        }
        WeightRandom<ServiceInstance> weightMeta = RandomUtil.weightRandom(weightList);
        if(ObjectUtils.isEmpty(weightMeta)){
            return getServiceInstanceEmptyResponse();
        }
        ServiceInstance serviceInstance = weightMeta.next();
        if(ObjectUtils.isEmpty(serviceInstance)){
            return getServiceInstanceEmptyResponse();
        }
        log.info(serviceInstance.getMetadata().get("nacos.weight"));
        return new DefaultResponse(serviceInstance);
    }

    private Response<ServiceInstance> getServiceInstanceEmptyResponse() {
        log.info("No servers available for service: {}",this.serviceId);
        return new EmptyResponse();
    }
}
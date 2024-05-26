package com.ssy.lingxi.component.nacos.loadbalancer;

import com.iuin.component.base.constants.ServiceHeaderConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.loadbalancer.core.DelegatingServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

/**
 * 自定义 根据服务名 获取服务实例 列表
 * <p>
 * 需求： 用户通过请求访问 网关<br />
 * 1、如果请求头中的 version 值和 下游服务元数据的 version 值一致，则选择该 服务。<br />
 * 2、如果请求头中的 version 值和 下游服务元数据的 version 值不一致，且 不存在 version 的值 为 default 则直接报错。<br />
 * 3、如果请求头中的 version 值和 下游服务元数据的 version 值不一致，且 存在 version 的值 为 default，则选择该服务。<br />
 * <p>
 * 参考: {@link org.springframework.cloud.loadbalancer.core.HintBasedServiceInstanceListSupplier} 实现
 *
 * @author huan.fu
 * @since 2023/6/19 - 21:14
 */
@Slf4j
public class VersionServiceInstanceListSupplier extends DelegatingServiceInstanceListSupplier {

    /**
     * 请求头的名字， 通过这个 version 字段和 服务中的元数据来version字段进行比较，
     * 得到最终的实例数据
     */
    private static final String VERSION_HEADER_NAME = ServiceHeaderConstant.HEADER_NACOS_VERSION;
    private static final String DEFAULT_NACOS_VERSION = "default";


    public VersionServiceInstanceListSupplier(ServiceInstanceListSupplier delegate) {
        super(delegate);
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return delegate.get();
    }

    @Override
    public Flux<List<ServiceInstance>> get(Request request) {
        return delegate.get(request).map(instances -> filteredByVersion(instances, getVersion(request.getContext())));
    }

    private String getVersion(Object requestContext) {
        if (requestContext == null) {
            return null;
        }
        String version = null;
        if (requestContext instanceof RequestDataContext) {
            version = getVersionFromHeader((RequestDataContext) requestContext);
        }
        log.info("获取到需要请求服务[{}]的version:[{}]", getServiceId(), version);
        return version;
    }

    /**
     * 从请求中获取version
     */
    private String getVersionFromHeader(RequestDataContext context) {
        if (context.getClientRequest() != null) {
            HttpHeaders headers = context.getClientRequest().getHeaders();
            if (headers != null) {
                return headers.getFirst(VERSION_HEADER_NAME);
            }
        }
        return null;
    }

    private List<ServiceInstance> filteredByVersion(List<ServiceInstance> instances, String version) {

        // 1、获取 请求头中的 version 和 ServiceInstance 中 元数据中 version 一致的服务
        List<ServiceInstance> selectServiceInstances = instances.stream().filter(
                instance -> Objects.equals(instance.getMetadata().get(VERSION_HEADER_NAME), version)
        ).toList();
        if (!selectServiceInstances.isEmpty()) {
            log.info("返回请求服务:[{}]为version:[{}]的有:[{}]个", getServiceId(), version, selectServiceInstances.size());
            return selectServiceInstances;
        }

        // 2、返回 version=default 的实例
        selectServiceInstances = instances.stream().filter(
                instance -> Objects.equals(instance.getMetadata().get(VERSION_HEADER_NAME), DEFAULT_NACOS_VERSION)
        ).toList();
        if (!selectServiceInstances.isEmpty()) {
            log.info("返回请求服务:[{}]为version:[{}]的有:[{}]个", getServiceId(), version, selectServiceInstances.size());
            return selectServiceInstances;
        }
        log.info("返回请求服务:[{}]为version:[{}]的有:[{}]个", getServiceId(), DEFAULT_NACOS_VERSION, selectServiceInstances.size());

        // 3、返回所有实例
        return instances;
    }
}
package com.iuin.commodity.config;

import com.iuin.common.utils.thread.CommonThreadUtil;
import com.iuin.component.base.handle.decorator.RequestTaskDecorator;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 营销服务线程池配置类
 *
 * @author 万宁
 * @version 3.0.0
 * @since 2023-10-24
 */
@Slf4j
@Configuration
public class ThreadPoolConfig {

    @Value("${spring.task.execution.pool.core-size:0}")
    private int corePoolSize;
    @Value("${spring.task.execution.pool.max-size:0}")
    private int maxPoolSize;
    @Value("${spring.task.execution.pool.queue-capacity:0}")
    private int queueCapacity;
    @Value("${spring.task.execution.thread-name-prefix:marketing-task-thread-}")
    private String namePrefix;
    @Value("${spring.task.execution.pool.keep-alive:60s}")
    private Duration keepAlive;

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = defualtThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("commodity-threadPoolTaskExecutor-");
        return threadPoolTaskExecutor;
    }

    @Bean(name = "commodityAsyncThreadPoolExecutor")
    public ThreadPoolTaskExecutor commodityAsyncThreadPoolExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = defualtThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("commodity-commodityAsyncThreadPoolExecutor-");
        return threadPoolTaskExecutor;
    }

    /**
     * 创建默认线程池
     */
    @NotNull
    private ThreadPoolTaskExecutor defualtThreadPoolTaskExecutor() {
        int recommendIOCorePoolSize = CommonThreadUtil.recommendIOCorePoolSize();
        int recommendIOMaxPoolSize = CommonThreadUtil.recommendIOMaxPoolSize();
        int recommendIoQueueCapacity = CommonThreadUtil.RECOMMEND_IO_QUEUE_CAPACITY;

        log.info("marketing-service threadPoolTaskExecutor init: " +
                        "corePoolSize: {}, maxPoolSize: {}, queueCapacity: {}, namePrefix: {}, keepAlive: {}, " +
                        "recommendIOCorePoolSize: {}, recommendIOMaxPoolSize: {}, recommendIoQueueCapacity: {}",
                corePoolSize, maxPoolSize, queueCapacity, namePrefix, keepAlive,
                recommendIOCorePoolSize, recommendIOMaxPoolSize, recommendIoQueueCapacity);

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        taskExecutor.setCorePoolSize(corePoolSize == 0 ? recommendIOCorePoolSize : corePoolSize);
        //配置最大线程数
        taskExecutor.setMaxPoolSize(maxPoolSize == 0 ? recommendIOMaxPoolSize : maxPoolSize);
        //配置队列大小
        taskExecutor.setQueueCapacity(queueCapacity == 0 ? recommendIoQueueCapacity : queueCapacity);
        //配置线程池中的线程的名称前缀
        taskExecutor.setThreadNamePrefix(namePrefix);

        //线程池Keep-Alive的时间，默认为60秒
        taskExecutor.setKeepAliveSeconds((int) keepAlive.getSeconds());

        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 同步请求以及语言
        taskExecutor.setTaskDecorator(new RequestTaskDecorator());

        //这行代码可以不要
        taskExecutor.initialize();
        return taskExecutor;
    }
}

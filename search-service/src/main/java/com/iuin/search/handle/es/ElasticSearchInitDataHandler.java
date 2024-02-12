package com.iuin.search.handle.es;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.util.ClassUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * es索引初始化
 *
 * @author fa
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ElasticSearchInitDataHandler {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final String entityPackage = "com.iuin.search.entity.es";

    // 用于创建索引和添加映射
    @PostConstruct
    public void init() {
        createIndexForAllEntities();
    }

    public void createIndexForAllEntities() {
        // 遍历包下所有的实体类，使用Spring提供的ClassPathScanningCandidateComponentProvider来扫描指定包下的所有类
        // 创建类扫描器
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

        // 添加过滤器用于扫描带有@Document注解的类
        scanner.addIncludeFilter(new AnnotationTypeFilter(Document.class));

        // 调用findCandidateComponents()方法，将实体类所在的包名作为参数传递给它。
        // 返回一个Set类型的结果集，其中包含了实体类所在包下所有带有@Document注解的类。
        Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(entityPackage);

        // 使用Java 8的stream()流和map()方法将Set类型转换为List<Class<?>>类型，并将其赋值给classes变量。
        // 将bean定义转化为Class对象
        List<? extends Class<?>> classes = beanDefinitions.stream().map(BeanDefinition::getBeanClassName).filter(Objects::nonNull).map(beanClassName -> ClassUtils.resolveClassName(
                beanClassName, this.getClass().getClassLoader()
        )).toList();

        // for循环遍历所有实体类，检查索引是否存在，如果不存在，则创建新的索引和映射。
        for (Class<?> clazz : classes) {

            // 获取Document注解中的索引名
            Document document = clazz.getAnnotation(Document.class);
            String indexName = document.indexName();

            // elasticsearch8.x使用的是新的api，与之前版本有些不一样，我目前也在看文档学习
            // 判断索引是否已经存在
            if (!elasticsearchTemplate.indexOps(clazz).exists()) {
                try {
                    // 使用为这个IndexOperations绑定的实体定义的设置和映射创建一个索引。
                    elasticsearchTemplate.indexOps(clazz).createWithMapping();
                    log.info("已根据bean对象创建索引并添加映射: {}", indexName);
                } catch (Exception e) {
                    log.error("添加索引失败: {}", indexName, e);
                }
            } else {
                log.info("已存在索引: {}", indexName);
            }
        }
    }
}
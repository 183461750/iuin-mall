package com.iuin.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author fa
 */
//@EnableElasticsearchRepositories(basePackages = {"com.iuin.search.repository.es"})
@EnableDiscoveryClient
@SpringBootApplication
public class SearchServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchServiceApplication.class, args);
    }

}

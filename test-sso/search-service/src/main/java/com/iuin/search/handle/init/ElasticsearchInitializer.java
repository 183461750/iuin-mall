package com.iuin.search.handle.init;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ElasticsearchInitializer {

    @Resource
    private RestHighLevelClient client;

    @PostConstruct
    public void initIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("test_commodity");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 0)
        );
        client.indices().create(request, RequestOptions.DEFAULT);
    }

}
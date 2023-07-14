package com.iuin.search.service.impl;

import cn.hutool.core.util.StrUtil;
import com.iuin.component.es.constants.EsIndexConstant;
import com.iuin.search.domain.CommodityEsDM;
import com.iuin.search.entity.es.CommodityEs;
import com.iuin.search.model.TestAddReq;
import com.iuin.search.model.TestGetReq;
import com.iuin.search.repository.es.CommodityEsRepository;
import com.iuin.search.service.TestService;
import jakarta.annotation.Resource;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * <p>TODO</p>
 *
 * @author fa
 */
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private CommodityEsRepository commodityEsRepository;
    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public String get(TestGetReq testGetReq) {

        SearchRequest searchRequest = new SearchRequest(EsIndexConstant.TEST_COMMODITY);

        //构建查询语句
        SearchSourceBuilder searchSourceBuilder = getSearchSourceBuilder(testGetReq);

        //是否需要高亮
        if (testGetReq.getIsHighlight()) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("name");  //为name字段创建字段高光色。
            highlightBuilder.field(highlightTitle);
            highlightBuilder.preTags("<span class='highlight'>");
            highlightBuilder.postTags("</span>");
            searchSourceBuilder.highlighter(highlightBuilder);
        }
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public CommodityEs add(TestAddReq req) {
        return commodityEsRepository.save(CommodityEsDM.buildBy(req));
    }

    private SearchSourceBuilder getSearchSourceBuilder(TestGetReq testGetReq) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().trackTotalHits(true);

        //分页参数
        int current = testGetReq.getCurrent() - 1;
        int pageSize = testGetReq.getPageSize();
        searchSourceBuilder.from(current * pageSize);
        searchSourceBuilder.size(pageSize);


        //构建查询条件
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        if (StrUtil.isNotBlank(testGetReq.getTest())) {
            query.must(QueryBuilders.matchQuery("test", testGetReq.getTest()));
        }
        searchSourceBuilder.query(query);

        return searchSourceBuilder;
    }
}

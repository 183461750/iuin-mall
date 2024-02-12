package com.iuin.search.repository.es;

import com.iuin.search.entity.es.CommodityEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 商品ES搜索引擎层
 *
 * @author Fa
 */
@Repository
public interface CommodityEsRepository extends ElasticsearchRepository<CommodityEs, Long> {

}

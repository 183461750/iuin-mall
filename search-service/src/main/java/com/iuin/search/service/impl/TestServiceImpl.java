package com.iuin.search.service.impl;

import com.iuin.search.domain.CommodityEsDM;
import com.iuin.search.entity.es.CommodityEs;
import com.iuin.search.model.TestAddReq;
import com.iuin.search.repository.es.CommodityEsRepository;
import com.iuin.search.service.TestService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author fa
 */
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private CommodityEsRepository commodityEsRepository;

    @Override
    public CommodityEs add(TestAddReq req) {
        return commodityEsRepository.save(CommodityEsDM.buildBy(req));
    }

}

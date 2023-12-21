package com.iuin.search.service;

import com.iuin.search.entity.es.CommodityEs;
import com.iuin.search.model.TestAddReq;
import com.iuin.search.model.TestGetReq;

/**
 * @author fa
 */
public interface TestService {

    CommodityEs add(TestAddReq req);

}

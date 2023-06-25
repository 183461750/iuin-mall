package com.iuin.search.service;

import com.iuin.search.model.TestAddReq;
import com.iuin.search.model.TestGetReq;
import com.iuin.search.entity.es.CommodityEs;

/**
 * @author fa
 */
public interface TestService {

    String get(TestGetReq testGetReq);

    CommodityEs add(TestAddReq req);

}

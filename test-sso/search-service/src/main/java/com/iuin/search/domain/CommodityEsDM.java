package com.iuin.search.domain;

import com.iuin.search.model.TestAddReq;
import com.iuin.search.entity.es.CommodityEs;

/**
 *
 * @author fa
 */
public class CommodityEsDM {

    public static CommodityEs buildBy(TestAddReq req) {
        CommodityEs commodityEs = new CommodityEs();
        commodityEs.setName(req.getName());
        commodityEs.setMin(req.getMin());
        commodityEs.setMax(req.getMax());
        commodityEs.setTest(req.getTest());
        return commodityEs;
    }

}

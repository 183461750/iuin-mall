package com.iuin.search.service;

import com.iuin.search.api.model.req.CategoryReq;
import com.iuin.search.api.model.resp.CategoryResp;

/**
 * @author fa
 */
public interface ICategoryService {

    CategoryResp info(CategoryReq categoryReq);

}

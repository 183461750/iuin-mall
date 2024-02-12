package com.iuin.mall.client.businessservice.create_commodity_sales_area_template;

import com.wakedata.common.core.dto.PageResultDTO;
import com.wakedata.common.core.dto.ResultDTO;
import com.iuin.mall.client.businessservice.create_commodity_sales_area_template.query.Untitled;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

/**
 * 创建商品销售区域模版-RPC能力接口
 */
public interface Create_Commodity_Sales_Area_TemplateRpcService {

    /** 商品销售区域 */
    ResultDTO<Void> untitled(Untitled request);

}
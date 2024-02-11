package com.iuin.mall.client.commodity_sales_area;

import com.wakedata.common.core.dto.PageResultDTO;
import com.wakedata.common.core.dto.ResultDTO;
import com.iuin.mall.client.commodity_sales_area.query.CommoditySalesAreaCreateDTO;
import com.iuin.mall.client.commodity_sales_area.query.CommoditySalesAreaModifyDTO;
import com.iuin.mall.client.commodity_sales_area.query.CommoditySalesAreaRemoveDTO;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

/**
 * 商品销售区域-RPC能力接口
 */
public interface Commodity_Sales_AreaRpcService {

    /** 新增商品销售区域 */
    ResultDTO<Long> create(CommoditySalesAreaCreateDTO dto);

    /** 编辑商品销售区域 */
    ResultDTO<Boolean> modify(CommoditySalesAreaModifyDTO dto);

    /** 删除商品销售区域 */
    ResultDTO<Boolean> remove(CommoditySalesAreaRemoveDTO dto);

}
package com.iuin.mall.commodity_sales_area.client;

import com.wakedata.common.core.dto.PageResultDTO;
import com.wakedata.common.core.dto.ResultDTO;
import com.iuin.mall.commodity_sales_area.client.query.Findxxx;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaCreateDTO;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaModifyDTO;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaRemoveDTO;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

/**
 * 商品销售区域-RPC能力接口
 *
 * @author visual-ddd
 * @since 1.0
 */
public interface Commodity_Sales_AreaRpcService {

    /** 新增商品销售区域 */
    ResultDTO<Long> create(CommoditySalesAreaCreateDTO dto);

    /** 编辑商品销售区域 */
    ResultDTO<Boolean> modify(CommoditySalesAreaModifyDTO dto);

    /** 删除商品销售区域 */
    ResultDTO<Boolean> remove(CommoditySalesAreaRemoveDTO dto);

    /** 查询xxx */
    ResultDTO<Void> findxxx(Findxxx query);

}
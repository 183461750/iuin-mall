package com.iuin.mall.commodity_sales_area.client;

import com.wakedata.common.core.dto.PageResultDTO;
import com.wakedata.common.core.dto.ResultDTO;
import com.iuin.mall.commodity_sales_area.client.query.Findxxx;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaCreateDTO;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaModifyDTO;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaRemoveDTO;
import com.iuin.mall.commodity_sales_area.app.Commodity_Sales_AreaApplication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

/**
 * 商品销售区域-RPC能力接口实现
 *
 * @author visual-ddd
 * @since 1.0
 */
@Service
public class Commodity_Sales_AreaRpcServiceImpl implements Commodity_Sales_AreaRpcService {

    @Resource
    private Commodity_Sales_AreaApplication application;

    @Override
    public ResultDTO<Long> create(CommoditySalesAreaCreateDTO dto) {
        return application.create(dto);
    }

    @Override
    public ResultDTO<Boolean> modify(CommoditySalesAreaModifyDTO dto) {
        return application.modify(dto);
    }

    @Override
    public ResultDTO<Boolean> remove(CommoditySalesAreaRemoveDTO dto) {
        return application.remove(dto);
    }

    @Override
    public ResultDTO<Void> findxxx(Findxxx query) {
        return application.findxxx(query);
    }
}
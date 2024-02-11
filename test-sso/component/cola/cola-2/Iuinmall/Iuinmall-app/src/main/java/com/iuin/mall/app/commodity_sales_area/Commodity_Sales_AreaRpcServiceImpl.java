package com.iuin.mall.app.commodity_sales_area;

import com.wakedata.common.core.dto.PageResultDTO;
import com.wakedata.common.core.dto.ResultDTO;
import com.iuin.mall.client.commodity_sales_area.Commodity_Sales_AreaRpcService;
import com.iuin.mall.client.commodity_sales_area.query.CommoditySalesAreaCreateDTO;
import com.iuin.mall.client.commodity_sales_area.query.CommoditySalesAreaModifyDTO;
import com.iuin.mall.client.commodity_sales_area.query.CommoditySalesAreaRemoveDTO;
import com.iuin.mall.app.commodity_sales_area.assembler.CommoditySalesAreaCreateDTO2CommoditySalesAreaCreateCmdConvert;
import com.iuin.mall.app.commodity_sales_area.assembler.CommoditySalesAreaModifyDTO2CommoditySalesAreaModifyCmdConvert;
import com.iuin.mall.app.commodity_sales_area.assembler.CommoditySalesAreaRemoveDTO2CommoditySalesAreaRemoveCmdConvert;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.create.CommoditySalesAreaCreateCmdHandler;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.modify.CommoditySalesAreaModifyCmdHandler;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.remove.CommoditySalesAreaRemoveCmdHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

/**
 * 商品销售区域-RPC能力接口实现
 */
@Service
public class Commodity_Sales_AreaRpcServiceImpl implements Commodity_Sales_AreaRpcService {

    @Resource
    private CommoditySalesAreaCreateCmdHandler commoditySalesAreaCreateCmdHandler;
    @Resource
    private CommoditySalesAreaModifyCmdHandler commoditySalesAreaModifyCmdHandler;
    @Resource
    private CommoditySalesAreaRemoveCmdHandler commoditySalesAreaRemoveCmdHandler;


    @Override
    public ResultDTO<Long> create(CommoditySalesAreaCreateDTO dto) {
        Long id = commoditySalesAreaCreateCmdHandler.handle(CommoditySalesAreaCreateDTO2CommoditySalesAreaCreateCmdConvert.INSTANCE.dto2Do(dto));
        return ResultDTO.success(id);
    }

    @Override
    public ResultDTO<Boolean> modify(CommoditySalesAreaModifyDTO dto) {
        commoditySalesAreaModifyCmdHandler.handle(CommoditySalesAreaModifyDTO2CommoditySalesAreaModifyCmdConvert.INSTANCE.dto2Do(dto));
        return ResultDTO.success(Boolean.TRUE);
    }

    @Override
    public ResultDTO<Boolean> remove(CommoditySalesAreaRemoveDTO dto) {
        commoditySalesAreaRemoveCmdHandler.handle(CommoditySalesAreaRemoveDTO2CommoditySalesAreaRemoveCmdConvert.INSTANCE.dto2Do(dto));
        return ResultDTO.success(Boolean.TRUE);
    }
}
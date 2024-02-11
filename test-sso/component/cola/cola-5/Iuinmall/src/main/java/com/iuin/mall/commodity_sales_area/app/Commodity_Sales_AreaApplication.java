package com.iuin.mall.commodity_sales_area.app;

import com.wakedata.common.core.dto.PageResultDTO;
import com.wakedata.common.core.dto.ResultDTO;
import com.iuin.mall.commodity_sales_area.client.Commodity_Sales_AreaRpcService;
import com.iuin.mall.commodity_sales_area.client.query.Findxxx;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaCreateDTO;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaModifyDTO;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaRemoveDTO;
import com.iuin.mall.commodity_sales_area.app.view.FindxxxExe;
import com.iuin.mall.commodity_sales_area.app.assembler.CommoditySalesAreaCreateDTO2CommoditySalesAreaCreateCmdConvert;
import com.iuin.mall.commodity_sales_area.app.assembler.CommoditySalesAreaModifyDTO2CommoditySalesAreaModifyCmdConvert;
import com.iuin.mall.commodity_sales_area.app.assembler.CommoditySalesAreaRemoveDTO2CommoditySalesAreaRemoveCmdConvert;
import com.iuin.mall.commodity_sales_area.app.cmd.create.CommoditySalesAreaCreateCmdHandler;
import com.iuin.mall.commodity_sales_area.app.cmd.modify.CommoditySalesAreaModifyCmdHandler;
import com.iuin.mall.commodity_sales_area.app.cmd.remove.CommoditySalesAreaRemoveCmdHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

/**
 * 商品销售区域
 *
 * @author visual-ddd
 * @since 1.0
 */
@Service
public class Commodity_Sales_AreaApplication {

    @Resource
    private CommoditySalesAreaCreateCmdHandler commoditySalesAreaCreateCmdHandler;
    @Resource
    private CommoditySalesAreaModifyCmdHandler commoditySalesAreaModifyCmdHandler;
    @Resource
    private CommoditySalesAreaRemoveCmdHandler commoditySalesAreaRemoveCmdHandler;
    @Resource
    private FindxxxExe findxxxExe;


    public ResultDTO<Long> create(CommoditySalesAreaCreateDTO dto) {
        Long id = commoditySalesAreaCreateCmdHandler.handle(CommoditySalesAreaCreateDTO2CommoditySalesAreaCreateCmdConvert.INSTANCE.dto2Do(dto));
        return ResultDTO.success(id);
    }

    public ResultDTO<Boolean> modify(CommoditySalesAreaModifyDTO dto) {
        commoditySalesAreaModifyCmdHandler.handle(CommoditySalesAreaModifyDTO2CommoditySalesAreaModifyCmdConvert.INSTANCE.dto2Do(dto));
        return ResultDTO.success(Boolean.TRUE);
    }

    public ResultDTO<Boolean> remove(CommoditySalesAreaRemoveDTO dto) {
        commoditySalesAreaRemoveCmdHandler.handle(CommoditySalesAreaRemoveDTO2CommoditySalesAreaRemoveCmdConvert.INSTANCE.dto2Do(dto));
        return ResultDTO.success(Boolean.TRUE);
    }

    public ResultDTO<Void> findxxx(Findxxx query) {
        return findxxxExe.execute(query);
    }
}
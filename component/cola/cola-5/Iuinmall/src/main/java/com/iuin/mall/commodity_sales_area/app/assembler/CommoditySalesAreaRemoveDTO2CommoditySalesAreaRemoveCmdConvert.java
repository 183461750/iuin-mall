package com.iuin.mall.commodity_sales_area.app.assembler;

import com.iuin.mall.commodity_sales_area.infrastructure.BaseConvert;
import com.iuin.mall.commodity_sales_area.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaRemoveDTO;
import com.iuin.mall.commodity_sales_area.app.cmd.remove.CommoditySalesAreaRemoveCmd;

@Mapper(uses = {
        BaseJsonConvertor.class,
        AreaDetailRequest2AreaDetailConvert.class,
        CommoditySalesAreaRequest2CommoditySalesAreaConvert.class,
})
public interface CommoditySalesAreaRemoveDTO2CommoditySalesAreaRemoveCmdConvert extends BaseConvert<CommoditySalesAreaRemoveDTO, CommoditySalesAreaRemoveCmd> {

    CommoditySalesAreaRemoveDTO2CommoditySalesAreaRemoveCmdConvert INSTANCE = Mappers.getMapper(CommoditySalesAreaRemoveDTO2CommoditySalesAreaRemoveCmdConvert.class);
}
package com.iuin.mall.app.commodity_sales_area.assembler;

import com.iuin.mall.infrastructure.BaseConvert;
import com.iuin.mall.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.iuin.mall.client.commodity_sales_area.query.CommoditySalesAreaRemoveDTO;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.remove.CommoditySalesAreaRemoveCmd;

@Mapper(uses = {
        BaseJsonConvertor.class,
})
public interface CommoditySalesAreaRemoveDTO2CommoditySalesAreaRemoveCmdConvert extends BaseConvert<CommoditySalesAreaRemoveDTO, CommoditySalesAreaRemoveCmd> {

    CommoditySalesAreaRemoveDTO2CommoditySalesAreaRemoveCmdConvert INSTANCE = Mappers.getMapper(CommoditySalesAreaRemoveDTO2CommoditySalesAreaRemoveCmdConvert.class);
}
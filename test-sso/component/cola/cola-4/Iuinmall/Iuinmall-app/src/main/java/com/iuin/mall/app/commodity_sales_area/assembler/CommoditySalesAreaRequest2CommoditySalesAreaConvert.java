package com.iuin.mall.app.commodity_sales_area.assembler;

import com.iuin.mall.infrastructure.BaseConvert;
import com.iuin.mall.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.iuin.mall.client.commodity_sales_area.query.CommoditySalesAreaRequest;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.CommoditySalesArea;
#importEnumConverter()

@Mapper(uses = {
        BaseJsonConvertor.class,
})
public interface CommoditySalesAreaRequest2CommoditySalesAreaConvert extends BaseConvert<CommoditySalesAreaRequest, CommoditySalesArea> {

    CommoditySalesAreaRequest2CommoditySalesAreaConvert INSTANCE = Mappers.getMapper(CommoditySalesAreaRequest2CommoditySalesAreaConvert.class);
}
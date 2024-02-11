package com.iuin.mall.app.commodity_sales_area.assembler;

import com.iuin.mall.infrastructure.BaseConvert;
import com.iuin.mall.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.iuin.mall.client.commodity_sales_area.query.CommoditySalesAreaCreateDTO;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.create.CommoditySalesAreaCreateCmd;

@Mapper(uses = {
        BaseJsonConvertor.class,
        UntitledRequest2UntitledConvert.class,
})
public interface CommoditySalesAreaCreateDTO2CommoditySalesAreaCreateCmdConvert extends BaseConvert<CommoditySalesAreaCreateDTO, CommoditySalesAreaCreateCmd> {

    CommoditySalesAreaCreateDTO2CommoditySalesAreaCreateCmdConvert INSTANCE = Mappers.getMapper(CommoditySalesAreaCreateDTO2CommoditySalesAreaCreateCmdConvert.class);
}
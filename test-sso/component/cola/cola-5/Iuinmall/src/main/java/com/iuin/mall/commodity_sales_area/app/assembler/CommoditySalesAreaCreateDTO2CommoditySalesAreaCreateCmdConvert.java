package com.iuin.mall.commodity_sales_area.app.assembler;

import com.iuin.mall.commodity_sales_area.infrastructure.BaseConvert;
import com.iuin.mall.commodity_sales_area.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaCreateDTO;
import com.iuin.mall.commodity_sales_area.app.cmd.create.CommoditySalesAreaCreateCmd;

@Mapper(uses = {
        BaseJsonConvertor.class,
        AreaDetailRequest2AreaDetailConvert.class,
        CommoditySalesAreaRequest2CommoditySalesAreaConvert.class,
})
public interface CommoditySalesAreaCreateDTO2CommoditySalesAreaCreateCmdConvert extends BaseConvert<CommoditySalesAreaCreateDTO, CommoditySalesAreaCreateCmd> {

    CommoditySalesAreaCreateDTO2CommoditySalesAreaCreateCmdConvert INSTANCE = Mappers.getMapper(CommoditySalesAreaCreateDTO2CommoditySalesAreaCreateCmdConvert.class);
}
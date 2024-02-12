package com.iuin.mall.app.commodity_sales_area.assembler;

import com.iuin.mall.infrastructure.BaseConvert;
import com.iuin.mall.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.iuin.mall.client.commodity_sales_area.query.CommoditySalesAreaModifyDTO;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.modify.CommoditySalesAreaModifyCmd;

@Mapper(uses = {
        BaseJsonConvertor.class,
        AreaDetailRequest2AreaDetailConvert.class,
        CommoditySalesAreaRequest2CommoditySalesAreaConvert.class,
})
public interface CommoditySalesAreaModifyDTO2CommoditySalesAreaModifyCmdConvert extends BaseConvert<CommoditySalesAreaModifyDTO, CommoditySalesAreaModifyCmd> {

    CommoditySalesAreaModifyDTO2CommoditySalesAreaModifyCmdConvert INSTANCE = Mappers.getMapper(CommoditySalesAreaModifyDTO2CommoditySalesAreaModifyCmdConvert.class);
}
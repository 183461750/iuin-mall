package com.iuin.mall.commodity_sales_area.app.assembler;

import com.iuin.mall.commodity_sales_area.infrastructure.BaseConvert;
import com.iuin.mall.commodity_sales_area.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaModifyDTO;
import com.iuin.mall.commodity_sales_area.app.cmd.modify.CommoditySalesAreaModifyCmd;

@Mapper(uses = {
        BaseJsonConvertor.class,
        AreaDetailRequest2AreaDetailConvert.class,
        CommoditySalesAreaRequest2CommoditySalesAreaConvert.class,
})
public interface CommoditySalesAreaModifyDTO2CommoditySalesAreaModifyCmdConvert extends BaseConvert<CommoditySalesAreaModifyDTO, CommoditySalesAreaModifyCmd> {

    CommoditySalesAreaModifyDTO2CommoditySalesAreaModifyCmdConvert INSTANCE = Mappers.getMapper(CommoditySalesAreaModifyDTO2CommoditySalesAreaModifyCmdConvert.class);
}
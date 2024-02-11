package com.iuin.mall.infrastructure.commodity_sales_area.assembler;

import com.iuin.mall.infrastructure.BaseConvert;
import com.iuin.mall.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.CommoditySalesArea;
import com.iuin.mall.infrastructure.commodity_sales_area.repository.model.CommoditySalesAreaDO;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.AreaDetailConverter;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.CommoditySalesAreaConverter;

@Mapper(uses = {
        BaseJsonConvertor.class,
        AreaDetailConverter.class,
        CommoditySalesAreaConverter.class,
})
public interface CommoditySalesArea2CommoditySalesAreaDOConvert extends BaseConvert<CommoditySalesArea, CommoditySalesAreaDO> {

    CommoditySalesArea2CommoditySalesAreaDOConvert INSTANCE = Mappers.getMapper(CommoditySalesArea2CommoditySalesAreaDOConvert.class);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "code", target = "code")
    @Override
    CommoditySalesAreaDO dto2Do(CommoditySalesArea dto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "code", target = "code")
    @Override
    CommoditySalesArea do2Dto(CommoditySalesAreaDO d);
}

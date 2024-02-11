package com.iuin.mall.infrastructure.commodity_sales_area.assembler;

import com.iuin.mall.infrastructure.BaseConvert;
import com.iuin.mall.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.CommoditySalesArea;
import com.iuin.mall.infrastructure.commodity_sales_area.repository.model.UntitledDO;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.UntitledConverter;

@Mapper(uses = {
        BaseJsonConvertor.class,
        UntitledConverter.class,
})
public interface CommoditySalesArea2UntitledDOConvert extends BaseConvert<CommoditySalesArea, UntitledDO> {

    CommoditySalesArea2UntitledDOConvert INSTANCE = Mappers.getMapper(CommoditySalesArea2UntitledDOConvert.class);

    @Mapping(source = "name", target = "name")
    @Override
    UntitledDO dto2Do(CommoditySalesArea dto);

    @Mapping(source = "name", target = "name")
    @Override
    CommoditySalesArea do2Dto(UntitledDO d);
}

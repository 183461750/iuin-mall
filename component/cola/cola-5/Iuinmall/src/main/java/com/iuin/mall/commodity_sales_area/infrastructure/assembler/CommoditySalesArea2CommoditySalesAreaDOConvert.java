package com.iuin.mall.commodity_sales_area.infrastructure.assembler;

import com.iuin.mall.commodity_sales_area.infrastructure.BaseConvert;
import com.iuin.mall.commodity_sales_area.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ${IMPORT_PACKAGE_MAP.get(${aggregationRootId}).get(${aggregationRootClass})}.CommoditySalesArea;
import com.iuin.mall.commodity_sales_area.infrastructure.repository.model.CommoditySalesAreaDO;
import com.iuin.mall.commodity_sales_area.domain.commoditysalesarea.AreaDetailConverter;
import ${IMPORT_PACKAGE_MAP.get(${entity.identity}).get("${entity.name}Converter")}.CommoditySalesAreaConverter;

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

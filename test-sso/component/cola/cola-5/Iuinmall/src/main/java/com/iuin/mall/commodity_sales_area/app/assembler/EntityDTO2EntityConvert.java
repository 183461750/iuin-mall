package ${IMPORT_PACKAGE_MAP.get(${ENTITY_ID}).get("${ENTITY_DTO_CLASS_NAME}2${ENTITY_CLASS_NAME}Convert")};

import com.iuin.mall.commodity_sales_area.infrastructure.BaseConvert;
import com.iuin.mall.commodity_sales_area.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ${IMPORT_PACKAGE_MAP.get(${ENTITY_ID}).get("${ENTITY_DTO_CLASS_NAME}")}.CommoditySalesAreaRequest;
import ${IMPORT_PACKAGE_MAP.get(${ENTITY_ID}).get(${ENTITY_CLASS_NAME})}.CommoditySalesArea;

@Mapper(uses = {
        BaseJsonConvertor.class,
})
public interface CommoditySalesAreaRequest2CommoditySalesAreaConvert extends BaseConvert<CommoditySalesAreaRequest, CommoditySalesArea> {

    CommoditySalesAreaRequest2CommoditySalesAreaConvert INSTANCE = Mappers.getMapper(CommoditySalesAreaRequest2CommoditySalesAreaConvert.class);
}
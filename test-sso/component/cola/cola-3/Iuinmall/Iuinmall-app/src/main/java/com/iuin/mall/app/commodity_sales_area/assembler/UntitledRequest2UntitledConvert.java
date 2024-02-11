package com.iuin.mall.app.commodity_sales_area.assembler;

import com.iuin.mall.infrastructure.BaseConvert;
import com.iuin.mall.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.iuin.mall.client.commodity_sales_area.query.UntitledRequest;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.Untitled;
#importEnumConverter()

@Mapper(uses = {
        BaseJsonConvertor.class,
})
public interface UntitledRequest2UntitledConvert extends BaseConvert<UntitledRequest, Untitled> {

    UntitledRequest2UntitledConvert INSTANCE = Mappers.getMapper(UntitledRequest2UntitledConvert.class);
}
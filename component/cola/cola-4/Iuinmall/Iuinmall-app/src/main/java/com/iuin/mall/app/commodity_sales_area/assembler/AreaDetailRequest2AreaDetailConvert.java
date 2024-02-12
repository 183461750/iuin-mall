package com.iuin.mall.app.commodity_sales_area.assembler;

import com.iuin.mall.infrastructure.BaseConvert;
import com.iuin.mall.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.iuin.mall.client.commodity_sales_area.query.AreaDetailRequest;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.AreaDetail;
#importEnumConverter()

@Mapper(uses = {
        BaseJsonConvertor.class,
})
public interface AreaDetailRequest2AreaDetailConvert extends BaseConvert<AreaDetailRequest, AreaDetail> {

    AreaDetailRequest2AreaDetailConvert INSTANCE = Mappers.getMapper(AreaDetailRequest2AreaDetailConvert.class);
}
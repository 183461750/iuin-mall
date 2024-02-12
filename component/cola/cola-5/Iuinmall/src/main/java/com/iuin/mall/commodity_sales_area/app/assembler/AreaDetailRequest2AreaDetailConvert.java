package com.iuin.mall.commodity_sales_area.app.assembler;

import com.iuin.mall.commodity_sales_area.infrastructure.BaseConvert;
import com.iuin.mall.commodity_sales_area.infrastructure.BaseJsonConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.iuin.mall.commodity_sales_area.client.query.AreaDetailRequest;
import com.iuin.mall.commodity_sales_area.domain.commoditysalesarea.AreaDetail;

@Mapper(uses = {
        BaseJsonConvertor.class,
})
public interface AreaDetailRequest2AreaDetailConvert extends BaseConvert<AreaDetailRequest, AreaDetail> {

    AreaDetailRequest2AreaDetailConvert INSTANCE = Mappers.getMapper(AreaDetailRequest2AreaDetailConvert.class);
}
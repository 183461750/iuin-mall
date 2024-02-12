package com.iuin.mall.app.commodity_sales_area.view;

import com.wakedata.common.core.dto.ResultDTO;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.iuin.mall.client.commodity_sales_area.query.Findxxx;
import com.iuin.mall.infrastructure.commodity_sales_area.repository.model.CommoditySalesAreaDO;
import com.iuin.mall.infrastructure.commodity_sales_area.repository.mapper.CommoditySalesAreaMapper;
import ${IMPORT_PACKAGE_MAP.get(${returnTypeId}).get(${returnTypeClassName})}.Iuinmall;
import ${IMPORT_PACKAGE_MAP.get(${objectMapperId}).get(${convertClassName})}.Iuinmall2CommoditySalesAreaDOConvert;

/**
 * 查询xxx-查询器
 */
@Component
public class FindxxxExe {

    @Resource
    private CommoditySalesAreaMapper mapper;

    public ResultDTO<Void> execute(Findxxx query) {
        return ResultDTO.success(
                Iuinmall2CommoditySalesAreaDOConvert.INSTANCE.do2Dto(mapper.findxxx(query)));
    }
}

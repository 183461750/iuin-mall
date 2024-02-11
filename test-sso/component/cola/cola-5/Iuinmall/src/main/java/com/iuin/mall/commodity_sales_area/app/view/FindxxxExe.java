package com.iuin.mall.commodity_sales_area.app.view;

import com.wakedata.common.core.dto.ResultDTO;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.iuin.mall.commodity_sales_area.client.query.Findxxx;
import ${IMPORT_PACKAGE_MAP.get(${doId}).get("${doClassName}")}.Iuinmall;
import ${IMPORT_PACKAGE_MAP.get(${doId}).get("${doMapperClassName}")}.${doMapperClassName};
import ${IMPORT_PACKAGE_MAP.get(${returnTypeId}).get(${returnTypeClassName})}.Iuinmall;
import ${IMPORT_PACKAGE_MAP.get(${objectMapperId}).get(${convertClassName})}.Iuinmall2IuinmallConvert;

/**
 * 查询xxx-查询器
 *
 * @author visual-ddd
 * @since 1.0
 */
@Component
public class FindxxxExe {

    @Resource
    private ${doMapperClassName} mapper;

    public ResultDTO<Void> execute(Findxxx query) {
        return ResultDTO.success(
                Iuinmall2IuinmallConvert.INSTANCE.do2Dto(mapper.findxxx(query)));
    }
}

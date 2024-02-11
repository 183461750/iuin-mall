package com.iuin.mall.commodity_sales_area.infrastructure.repository;

import com.wakedata.common.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import javax.annotation.Resource;
import org.springframework.util.Assert;
import ${IMPORT_PACKAGE_MAP.get(${aggregationId}).get("${aggregationClass}")}.CommoditySalesArea;
import ${IMPORT_PACKAGE_MAP.get(${aggregationId}).get("${aggregationClass}Repository")}.CommoditySalesAreaRepository;
import com.iuin.mall.commodity_sales_area.infrastructure.repository.model.CommoditySalesAreaDO;
import com.iuin.mall.commodity_sales_area.infrastructure.repository.mapper.CommoditySalesAreaMapper;
import com.iuin.mall.commodity_sales_area.infrastructure.assembler.CommoditySalesArea2CommoditySalesAreaDOConvert;

/**
 * CommoditySalesArea-聚合仓储实现类
 *
 * @author visual-ddd
 * @since 1.0
 */
@Slf4j
@Component
public class CommoditySalesAreaRepositoryImpl implements CommoditySalesAreaRepository {

    @Resource
    private CommoditySalesAreaMapper commoditySalesAreaMapper;

    @Override
    public CommoditySalesArea save(CommoditySalesArea commoditySalesArea) {
        CommoditySalesAreaDO commoditySalesAreaDO = CommoditySalesArea2CommoditySalesAreaDOConvert.INSTANCE.dto2Do(commoditySalesArea);
        int insert = commoditySalesAreaMapper.insert(commoditySalesAreaDO);
        Assert.isTrue(insert == 1, "插入数据库异常，请联系管理员");
        return CommoditySalesArea2CommoditySalesAreaDOConvert.INSTANCE.do2Dto(commoditySalesAreaDO);
    }

    @Override
    public CommoditySalesArea update(CommoditySalesArea commoditySalesArea) {
        CommoditySalesAreaDO commoditySalesAreaDO = CommoditySalesArea2CommoditySalesAreaDOConvert.INSTANCE.dto2Do(commoditySalesArea);
        int update = commoditySalesAreaMapper.updateById(commoditySalesAreaDO);
        Assert.isTrue(update == 1, "更新数据库异常，请联系管理员");
        return CommoditySalesArea2CommoditySalesAreaDOConvert.INSTANCE.do2Dto(commoditySalesAreaDO);
    }

    @Override
    public void remove(CommoditySalesArea commoditySalesArea) {
        CommoditySalesAreaDO commoditySalesAreaDO = CommoditySalesArea2CommoditySalesAreaDOConvert.INSTANCE.dto2Do(commoditySalesArea);
        commoditySalesAreaMapper.deleteById(commoditySalesAreaDO.getId());
    }

    @Override
    public CommoditySalesArea find(Long id) {
        CommoditySalesAreaDO result = commoditySalesAreaMapper.selectById(id);
        if (result == null) throw new BizException("id不存在!");
        return CommoditySalesArea2CommoditySalesAreaDOConvert.INSTANCE.do2Dto(result);
    }
}

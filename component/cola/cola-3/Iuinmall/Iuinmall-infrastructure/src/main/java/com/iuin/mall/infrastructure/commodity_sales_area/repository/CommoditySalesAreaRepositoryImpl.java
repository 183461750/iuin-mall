package com.iuin.mall.infrastructure.commodity_sales_area.repository;

import com.wakedata.common.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import javax.annotation.Resource;
import org.springframework.util.Assert;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.CommoditySalesArea;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.CommoditySalesAreaRepository;
import com.iuin.mall.infrastructure.commodity_sales_area.repository.model.UntitledDO;
import com.iuin.mall.infrastructure.commodity_sales_area.repository.mapper.UntitledMapper;
import com.iuin.mall.infrastructure.commodity_sales_area.assembler.CommoditySalesArea2UntitledDOConvert;

/**
 * CommoditySalesArea-聚合仓储实现类
 */
@Slf4j
@Component
public class CommoditySalesAreaRepositoryImpl implements CommoditySalesAreaRepository {

    @Resource
    private UntitledMapper untitledMapper;

    @Override
    public CommoditySalesArea save(CommoditySalesArea commoditySalesArea) {
        UntitledDO untitledDO = CommoditySalesArea2UntitledDOConvert.INSTANCE.dto2Do(commoditySalesArea);
        int insert = untitledMapper.insert(untitledDO);
        Assert.isTrue(insert == 1, "插入数据库异常，请联系管理员");
        return CommoditySalesArea2UntitledDOConvert.INSTANCE.do2Dto(untitledDO);
    }

    @Override
    public CommoditySalesArea update(CommoditySalesArea commoditySalesArea) {
        UntitledDO untitledDO = CommoditySalesArea2UntitledDOConvert.INSTANCE.dto2Do(commoditySalesArea);
        int update = untitledMapper.updateById(untitledDO);
        Assert.isTrue(update == 1, "更新数据库异常，请联系管理员");
        return CommoditySalesArea2UntitledDOConvert.INSTANCE.do2Dto(untitledDO);
    }

    @Override
    public void remove(CommoditySalesArea commoditySalesArea) {
        UntitledDO untitledDO = CommoditySalesArea2UntitledDOConvert.INSTANCE.dto2Do(commoditySalesArea);
        untitledMapper.deleteById(untitledDO.getId());
    }

    @Override
    public CommoditySalesArea find(Long id) {
        UntitledDO result = untitledMapper.selectById(id);
        if (result == null) throw new BizException("id不存在!");
        return CommoditySalesArea2UntitledDOConvert.INSTANCE.do2Dto(result);
    }
}

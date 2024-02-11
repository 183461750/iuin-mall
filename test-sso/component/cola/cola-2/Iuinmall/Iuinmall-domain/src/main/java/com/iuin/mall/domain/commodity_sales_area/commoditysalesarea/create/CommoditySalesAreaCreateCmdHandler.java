package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.create;

import com.wakedata.common.domainevent.DomainEventPublisher;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.*;

/**
 * 新增商品销售区域-指令处理器
 */
@Component
public class CommoditySalesAreaCreateCmdHandler {

    @Resource
    private CommoditySalesAreaRepository repository;
    @Resource
    private CommoditySalesAreaFactory factory;

    public Long handle(CommoditySalesAreaCreateCmd createCmd) {
        CommoditySalesArea entity = factory.getInstance(createCmd);

        CommoditySalesArea newEntity = repository.save(entity);

       // DomainEventPublisher.getInstance().postAfterCommit(new CommoditySalesAreaCreateEvent(createCmd));
        return newEntity.getId();
    }
}
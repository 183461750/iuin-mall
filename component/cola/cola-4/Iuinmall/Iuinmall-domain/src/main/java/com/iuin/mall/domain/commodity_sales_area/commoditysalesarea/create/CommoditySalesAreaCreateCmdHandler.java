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
    private CommoditySalesAreaTemplateRepository repository;
    @Resource
    private CommoditySalesAreaTemplateFactory factory;

    public Long handle(CommoditySalesAreaCreateCmd createCmd) {
        CommoditySalesAreaTemplate entity = factory.getInstance(createCmd);

        /* TODO CreateRule(创建规则) 规则描述 */

        CommoditySalesAreaTemplate newEntity = repository.save(entity);

       // DomainEventPublisher.getInstance().postAfterCommit(new CommoditySalesAreaCreateEvent(createCmd));
        return newEntity.getId();
    }
}
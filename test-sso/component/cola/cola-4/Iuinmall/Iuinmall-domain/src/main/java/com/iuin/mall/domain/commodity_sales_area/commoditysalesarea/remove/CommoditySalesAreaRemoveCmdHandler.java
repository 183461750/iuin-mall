package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.remove;

import com.wakedata.common.domainevent.DomainEventPublisher;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.*;

/**
 * 删除商品销售区域-指令处理器
 */
@Component
public class CommoditySalesAreaRemoveCmdHandler {

    @Resource
    private CommoditySalesAreaTemplateRepository repository;

    public void handle(CommoditySalesAreaRemoveCmd removeCmd) {
        CommoditySalesAreaTemplate commoditySalesAreaTemplate = repository.find(removeCmd.getId());
        commoditySalesAreaTemplate.remove(removeCmd);

        repository.remove(commoditySalesAreaTemplate);

       // DomainEventPublisher.getInstance().postAfterCommit(new CommoditySalesAreaRemoveEvent(removeCmd));
    }
}
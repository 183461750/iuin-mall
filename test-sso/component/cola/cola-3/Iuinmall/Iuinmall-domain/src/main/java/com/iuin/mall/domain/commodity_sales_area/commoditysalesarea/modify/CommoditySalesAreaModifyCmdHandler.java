package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.modify;

import com.wakedata.common.domainevent.DomainEventPublisher;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.*;

/**
 * 编辑商品销售区域-指令处理器
 */
@Component
public class CommoditySalesAreaModifyCmdHandler {

    @Resource
    private CommoditySalesAreaRepository repository;

    public void handle(CommoditySalesAreaModifyCmd updateCmd) {
        CommoditySalesArea commoditySalesArea = repository.find(updateCmd.getId());
        commoditySalesArea.modify(updateCmd);

        repository.update(commoditySalesArea);

       // DomainEventPublisher.getInstance().postAfterCommit(new CommoditySalesAreaModifyEvent(updateCmd));
    }
}
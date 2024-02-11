package com.iuin.mall.app.commodity_sales_area.consumer;

import com.wakedata.common.domainevent.annotation.DomainEventSubscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.create.CommoditySalesAreaCreateCmdHandler;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.modify.CommoditySalesAreaModifyCmdHandler;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.remove.CommoditySalesAreaRemoveCmdHandler;

import javax.annotation.Resource;

/**
 * Commodity_Sales_Area - 事件订阅
 */
@Slf4j
@Component
public class Commodity_Sales_AreaListener {

}
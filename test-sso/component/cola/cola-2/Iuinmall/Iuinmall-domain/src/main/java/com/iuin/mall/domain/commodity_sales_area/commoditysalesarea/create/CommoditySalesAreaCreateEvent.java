package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.create;

import com.wakedata.common.domainevent.model.BaseDomainEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.*;

/**
 * 新增商品销售区域-指令事件
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommoditySalesAreaCreateEvent extends BaseDomainEvent {
    
    /** 商品销售区域 ID */
    private Long id;

    /** 名称 */
    private String name;
    
    public CommoditySalesAreaCreateEvent() {}

    public CommoditySalesAreaCreateEvent(CommoditySalesAreaCreateCmd cmd
        , Long id
    ) {
        this.id = id;
        this.name = cmd.getName();
    }

    @Override
    public String eventCode() {
        return "${event.com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.create}";
    }
}

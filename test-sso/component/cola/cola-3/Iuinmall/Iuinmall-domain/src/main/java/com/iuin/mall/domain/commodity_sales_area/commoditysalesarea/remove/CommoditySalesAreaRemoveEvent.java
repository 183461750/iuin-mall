package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.remove;

import com.wakedata.common.domainevent.model.BaseDomainEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.*;

/**
 * 删除商品销售区域-指令事件
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommoditySalesAreaRemoveEvent extends BaseDomainEvent {
    
    /** 商品销售区域 ID */
    private Long id;
    
    public CommoditySalesAreaRemoveEvent() {}

    public CommoditySalesAreaRemoveEvent(CommoditySalesAreaRemoveCmd cmd
    ) {
        this.id = cmd.getId();
    }

    @Override
    public String eventCode() {
        return "${event.com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.remove}";
    }
}

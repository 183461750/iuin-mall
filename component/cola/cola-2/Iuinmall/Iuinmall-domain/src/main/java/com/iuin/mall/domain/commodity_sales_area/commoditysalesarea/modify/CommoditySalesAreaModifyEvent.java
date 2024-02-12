package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.modify;

import com.wakedata.common.domainevent.model.BaseDomainEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.*;

/**
 * 编辑商品销售区域-指令事件
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommoditySalesAreaModifyEvent extends BaseDomainEvent {
    
    /** 商品销售区域 ID */
    private Long id;

    /** 名称 */
    private String name;
    
    public CommoditySalesAreaModifyEvent() {}

    public CommoditySalesAreaModifyEvent(CommoditySalesAreaModifyCmd cmd
    ) {
        this.id = cmd.getId();
        this.name = cmd.getName();
    }

    @Override
    public String eventCode() {
        return "${event.com.iuin.mall.domain.commodity_sales_area.commoditysalesarea.modify}";
    }
}

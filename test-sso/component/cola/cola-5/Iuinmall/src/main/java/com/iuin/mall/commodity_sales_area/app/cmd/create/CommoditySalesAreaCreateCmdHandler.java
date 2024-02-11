package com.iuin.mall.commodity_sales_area.app.cmd.create;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iuin.mall.commodity_sales_area.domain.commoditysalesarea.*;

/**
 * 新增商品销售区域-指令处理器
 *
 * @author visual-ddd
 * @since 1.0
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
        return newEntity.getId();
    }
}
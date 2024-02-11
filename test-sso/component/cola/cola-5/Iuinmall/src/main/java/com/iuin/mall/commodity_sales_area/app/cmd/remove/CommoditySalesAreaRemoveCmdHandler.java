package com.iuin.mall.commodity_sales_area.app.cmd.remove;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iuin.mall.commodity_sales_area.domain.commoditysalesarea.*;

/**
 * 删除商品销售区域-指令处理器
 *
 * @author visual-ddd
 * @since 1.0
 */
@Component
public class CommoditySalesAreaRemoveCmdHandler {

    @Resource
    private CommoditySalesAreaTemplateRepository repository;

    public void handle(CommoditySalesAreaRemoveCmd removeCmd) {
        CommoditySalesAreaTemplate commoditySalesAreaTemplate = repository.find(removeCmd.getId());
        commoditySalesAreaTemplate.remove(removeCmd);

        repository.remove(commoditySalesAreaTemplate);
    }
}
package com.iuin.mall.commodity_sales_area.app.cmd.modify;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iuin.mall.commodity_sales_area.domain.commoditysalesarea.*;

/**
 * 编辑商品销售区域-指令处理器
 *
 * @author visual-ddd
 * @since 1.0
 */
@Component
public class CommoditySalesAreaModifyCmdHandler {

    @Resource
    private CommoditySalesAreaTemplateRepository repository;

    public void handle(CommoditySalesAreaModifyCmd updateCmd) {
        CommoditySalesAreaTemplate commoditySalesAreaTemplate = repository.find(updateCmd.getId());
        commoditySalesAreaTemplate.modify(updateCmd);

        repository.update(commoditySalesAreaTemplate);
    }
}
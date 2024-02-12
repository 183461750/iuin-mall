package com.iuin.mall.app.businessservice.create_commodity_sales_area_template;

import com.wakedata.common.core.dto.PageResultDTO;
import com.wakedata.common.core.dto.ResultDTO;
import com.iuin.mall.client.businessservice.create_commodity_sales_area_template.query.Untitled;
import com.iuin.mall.client.businessservice.create_commodity_sales_area_template.Create_Commodity_Sales_Area_TemplateRpcService;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

/**
 * 创建商品销售区域模版-RPC能力接口实现
 */
@Service
public class Create_Commodity_Sales_Area_TemplateRpcServiceImpl implements Create_Commodity_Sales_Area_TemplateRpcService {

    @Override
    public ResultDTO<Void> untitled(Untitled request) {

        return ResultDTO.success();
    }
}
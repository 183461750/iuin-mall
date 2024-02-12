package com.iuin.mall.create_commodity_sales_area_template.adapter.app;

import java.util.*;
import java.math.*;
import java.time.LocalDateTime;import java.time.LocalDateTime;
import javax.annotation.Resource;

import com.wakedata.common.core.dto.PageResultDTO;
import com.wakedata.common.core.dto.ResultDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import com.iuin.mall.create_commodity_sales_area_template.client.query.Untitled;
import com.iuin.mall.create_commodity_sales_area_template.client.Create_Commodity_Sales_Area_TemplateRpcService;

/**
 * 创建商品销售区域模版-C端
 *
 * @author visual-ddd
 * @since 1.0
 */
@RestController
@RequestMapping("/app/create_-commodity_-sales_-area_-template")
@Api(value = "/app/create_-commodity_-sales_-area_-template", tags = "创建商品销售区域模版-C端")
public class Create_Commodity_Sales_Area_TemplateAppController {

    @Resource
    private Create_Commodity_Sales_Area_TemplateRpcService create_Commodity_Sales_Area_TemplateRpcService;

    @ApiOperation("商品销售区域")
    @PostMapping("/untitled")
    public ResultDTO<Void> untitled(@RequestBody @Valid Untitled request) {
        return create_Commodity_Sales_Area_TemplateRpcService.untitled(request);
    }
}
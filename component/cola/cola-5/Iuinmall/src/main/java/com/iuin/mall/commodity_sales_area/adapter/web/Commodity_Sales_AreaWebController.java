package com.iuin.mall.commodity_sales_area.adapter.web;

import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import javax.annotation.Resource;

import com.wakedata.common.core.dto.PageResultDTO;
import com.wakedata.common.core.dto.ResultDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import com.iuin.mall.commodity_sales_area.client.Commodity_Sales_AreaRpcService;
import com.iuin.mall.commodity_sales_area.client.query.Findxxx;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaCreateDTO;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaModifyDTO;
import com.iuin.mall.commodity_sales_area.client.query.CommoditySalesAreaRemoveDTO;

/**
 * 商品销售区域-B端
 *
 * @author visual-ddd
 * @since 1.0
 */
@RestController
@RequestMapping("/web/commodity_-sales_-area")
@Api(value = "/web/commodity_-sales_-area", tags = "商品销售区域-B端")
public class Commodity_Sales_AreaWebController {

    @Resource
    private Commodity_Sales_AreaRpcService commodity_Sales_AreaRpcService;

    @ApiOperation("新增商品销售区域")
    @PostMapping("/create")
    public ResultDTO<Long> create(@RequestBody @Valid CommoditySalesAreaCreateDTO dto) {
        return commodity_Sales_AreaRpcService.create(dto);
    }

    @ApiOperation("编辑商品销售区域")
    @PostMapping("/modify")
    public ResultDTO<Boolean> modify(@RequestBody @Valid CommoditySalesAreaModifyDTO dto) {
        return commodity_Sales_AreaRpcService.modify(dto);
    }

    @ApiOperation("删除商品销售区域")
    @PostMapping("/remove")
    public ResultDTO<Boolean> remove(@RequestBody @Valid CommoditySalesAreaRemoveDTO dto) {
        return commodity_Sales_AreaRpcService.remove(dto);
    }

    @ApiOperation("查询xxx")
    @PostMapping("/findxxx")
    public ResultDTO<Void> findxxx(@RequestBody @Valid Findxxx query) {
        return commodity_Sales_AreaRpcService.findxxx(query);
    }
}
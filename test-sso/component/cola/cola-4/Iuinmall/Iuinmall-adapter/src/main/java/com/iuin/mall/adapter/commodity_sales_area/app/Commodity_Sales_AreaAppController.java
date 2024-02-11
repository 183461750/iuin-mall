package com.iuin.mall.adapter.commodity_sales_area.app;

import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
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
import com.iuin.mall.client.commodity_sales_area.Commodity_Sales_AreaRpcService;
import com.iuin.mall.client.commodity_sales_area.query.Findxxx;
import com.iuin.mall.client.commodity_sales_area.query.CommoditySalesAreaCreateDTO;
import com.iuin.mall.client.commodity_sales_area.query.CommoditySalesAreaModifyDTO;
import com.iuin.mall.client.commodity_sales_area.query.CommoditySalesAreaRemoveDTO;

/**
 * 商品销售区域-C端
 */
@RestController
@RequestMapping("/app/commodity_-sales_-area")
@Api(value = "/app/commodity_-sales_-area", tags = "商品销售区域-C端")
public class Commodity_Sales_AreaAppController {

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
    @GetMapping("/findxxx")
    public ResultDTO<Void> findxxx(Findxxx query) {
        return commodity_Sales_AreaRpcService.findxxx(query);
    }
}
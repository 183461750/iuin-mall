package com.iuin.mall.client.commodity_sales_area.query;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 删除商品销售区域
 */
@Data
@ApiModel(description = "删除商品销售区域")
public class CommoditySalesAreaRemoveDTO {

    @NotNull(message = "商品销售区域 ID不能为空！")
    @ApiModelProperty(value = "商品销售区域 ID" , required = true)
    private Long id;

}
package com.iuin.mall.commodity_sales_area.client.query;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 新增商品销售区域
 *
 * @author visual-ddd
 * @since 1.0
 */
@Data
@ApiModel(description = "新增商品销售区域")
public class CommoditySalesAreaCreateDTO {

    @NotNull(message = "名称不能为空！")
    @ApiModelProperty(value = "名称" , required = true)
    private String name;

}
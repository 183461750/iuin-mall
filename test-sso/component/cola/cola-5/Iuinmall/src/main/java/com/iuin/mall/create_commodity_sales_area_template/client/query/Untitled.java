package com.iuin.mall.create_commodity_sales_area_template.client.query;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import com.iuin.mall.create_commodity_sales_area_template.client.dto.*;
import javax.validation.constraints.NotNull;

/**
 * 商品销售区域
 *
 * @author visual-ddd
 * @since 1.0
 */
@Data
@ApiModel(description = "商品销售区域")
public class Untitled {

    @NotNull(message = "未命名不能为空！")
    @ApiModelProperty(value = "未命名" , required = true)
    private String untitled;

}
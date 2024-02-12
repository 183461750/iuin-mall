package com.iuin.mall.commodity_sales_area.client.query;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 区域详情
 *
 * @author visual-ddd
 * @since 1.0
 */
@Data
@ApiModel(description = "区域详情")
public class AreaDetailRequest {

    @NotNull(message = "详情名称不能为空！")
    @ApiModelProperty(value = "详情名称" , required = true)
    private String name;

    @NotNull(message = "详情不能为空！")
    @ApiModelProperty(value = "详情" , required = true)
    private String detail;

}
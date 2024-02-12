package com.iuin.mall.client.commodity_sales_area.query;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 区域详情
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
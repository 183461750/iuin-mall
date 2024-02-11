package com.iuin.mall.client.commodity_sales_area.query;

import com.wakedata.common.core.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import com.iuin.mall.client.commodity_sales_area.dto.*;
import javax.validation.constraints.NotNull;

/**
 * 查询xxx
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询xxx")
public class Findxxx extends BaseQuery {

    @NotNull(message = "名称不能为空！")
    @ApiModelProperty(value = "名称" , required = true)
    private String name;

}
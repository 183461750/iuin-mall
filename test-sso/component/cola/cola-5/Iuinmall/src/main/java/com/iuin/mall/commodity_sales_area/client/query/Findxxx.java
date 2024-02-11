package com.iuin.mall.commodity_sales_area.client.query;

import com.wakedata.common.core.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import com.iuin.mall.commodity_sales_area.client.dto.*;
import javax.validation.constraints.NotNull;

/**
 * 查询xxx
 *
 * @author visual-ddd
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "查询xxx")
public class Findxxx extends BaseQuery {

    @NotNull(message = "名称不能为空！")
    @ApiModelProperty(value = "名称" , required = true)
    private String name;

}
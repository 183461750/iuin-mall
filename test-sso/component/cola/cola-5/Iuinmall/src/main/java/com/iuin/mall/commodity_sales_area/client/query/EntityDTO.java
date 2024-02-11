package ${IMPORT_PACKAGE_MAP.get(${ENTITY_ID}).get("${ENTITY_DTO_CLASS_NAME}")};

import io.swagger.annotations.ApiModel;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 商品销售区域
 *
 * @author visual-ddd
 * @since 1.0
 */
@Data
@ApiModel(description = "商品销售区域")
public class CommoditySalesAreaRequest {

    @NotNull(message = "名称不能为空！")
    @ApiModelProperty(value = "名称" , required = true)
    private String name;

    @NotNull(message = "区域详情不能为空！")
    @ApiModelProperty(value = "区域详情" , required = true)
    private AreaDetailRequest areaDetail;

    @NotNull(message = "编码不能为空！")
    @ApiModelProperty(value = "编码" , required = true)
    private String code;

}
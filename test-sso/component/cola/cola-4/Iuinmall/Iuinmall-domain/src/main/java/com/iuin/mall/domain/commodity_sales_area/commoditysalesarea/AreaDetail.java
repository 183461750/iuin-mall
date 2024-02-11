package com.iuin.mall.domain.commodity_sales_area.commoditysalesarea;

import lombok.Data;
import java.util.*;
import java.math.*;
import java.time.LocalDateTime;

/**
 * 区域详情-值对象
 */
@Data
public class AreaDetail {

    /** 详情名称 */
    private String name;

    /** 详情 */
    private String detail;

}
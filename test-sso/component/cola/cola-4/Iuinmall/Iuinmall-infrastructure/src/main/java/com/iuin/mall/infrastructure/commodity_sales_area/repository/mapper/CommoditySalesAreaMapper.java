package com.iuin.mall.infrastructure.commodity_sales_area.repository.mapper;

import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.iuin.mall.infrastructure.commodity_sales_area.repository.model.CommoditySalesAreaDO;
import com.iuin.mall.client.commodity_sales_area.query.Findxxx;

/**
 * CommoditySalesAreaMapper接口
 */
@Mapper
public interface CommoditySalesAreaMapper extends BaseMapper<CommoditySalesAreaDO> {

    /** 查询xxx */
    CommoditySalesAreaDO findxxx(Findxxx query);

}
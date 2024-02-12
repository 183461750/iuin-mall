package com.iuin.mall.commodity_sales_area.infrastructure.repository.mapper;

import java.util.*;
import java.math.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.iuin.mall.commodity_sales_area.infrastructure.repository.model.CommoditySalesAreaDO;
import com.iuin.mall.commodity_sales_area.client.query.Findxxx;

/**
 * CommoditySalesAreaMapper接口
 *
 * @author visual-ddd
 * @since 1.0
 */
@Mapper
public interface CommoditySalesAreaMapper extends BaseMapper<CommoditySalesAreaDO> {

    /**
      * 查询xxx
      */
    CommoditySalesAreaDO findxxx(Findxxx query);

}
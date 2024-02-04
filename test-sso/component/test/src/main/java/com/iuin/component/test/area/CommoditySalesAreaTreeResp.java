package com.iuin.component.test.area;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 商品销售区域-树
 *
 * @author fa
 */
@Data
@AllArgsConstructor
public class CommoditySalesAreaTreeResp {

    /**
     * id
     */
    private String id;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 子节点
     */
    private List<CommoditySalesAreaTreeResp> children;

}

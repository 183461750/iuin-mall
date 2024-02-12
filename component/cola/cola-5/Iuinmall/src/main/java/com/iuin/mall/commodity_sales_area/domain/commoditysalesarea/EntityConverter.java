package ${IMPORT_PACKAGE_MAP.get(${ENTITY_ID}).get("${ENTITY_CLASS_NAME}Converter")};

import cn.hutool.json.JSONUtil;

import java.util.List;

/**
 * 商品销售区域-实体JSON转换器
 *
 * @author visual-ddd
 * @since 1.0
 */
public class CommoditySalesAreaConverter {

    public String entity2Json(CommoditySalesArea entity) {
        return JSONUtil.toJsonStr(entity);
    }

    public CommoditySalesArea json2Entity(String value) {
        return JSONUtil.toBean(value, CommoditySalesArea.class);
    }

    public String list2json(List<CommoditySalesArea> entityList) {
        return JSONUtil.toJsonStr(entityList);
    }

    public List<CommoditySalesArea> json2list(String value) {
        return JSONUtil.toList(value, CommoditySalesArea.class);
    }

}
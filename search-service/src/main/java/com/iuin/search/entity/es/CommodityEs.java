package com.iuin.search.entity.es;

import com.iuin.component.es.constants.EsIndexConstant;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;

/**
 * @author fa
 */
@Data
@Document(indexName = EsIndexConstant.TEST_COMMODITY)
@Setting(settingPath = "elasticsearch/settings.json")
public class CommodityEs {

    /**
     * 主键id
     */
    private Long id;
    /**
     * 商品名称
     */
    @MultiField(mainField = @Field(type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_max_word"),
            otherFields = @InnerField(suffix = "inner", type = FieldType.Text, analyzer = "pinyin"))
    private String name;
    /**
     * 最小(价格/积分)
     */
    @Field(type = FieldType.Double)
    private BigDecimal min;
    /**
     * 最大(价格/积分)
     */
    @Field(type = FieldType.Double)
    private BigDecimal max;
    /**
     * 测试字段
     */
    private String test;

}

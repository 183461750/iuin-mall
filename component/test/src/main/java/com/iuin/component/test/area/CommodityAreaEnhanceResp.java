package com.iuin.component.test.area;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author fa
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CommodityAreaEnhanceResp extends CommodityAreaResp {

    /**
     * 构造方法
     */
    public CommodityAreaEnhanceResp(String provinceCode, String provinceName, String cityCode, String cityName, String regionCode, String regionName) {
        super(provinceCode, provinceName, cityCode, cityName, regionCode, regionName);
    }

}

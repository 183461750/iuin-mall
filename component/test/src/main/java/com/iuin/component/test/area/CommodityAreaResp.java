package com.iuin.component.test.area;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 物流信息
 * @author jw.chen
 * @version 2.0.0
 * @since 2020/7/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommodityAreaResp implements Serializable {
    private static final long serialVersionUID = -669216125732639040L;
    /**
     * 省级编码
     */
    private String provinceCode;

    /**
     * 省级名称
     */
    private String provinceName;

    /**
     * 是否不限制城市
     */
    private Boolean isAllCity;

    /**
     * 市级编码
     */
    private String cityCode;

    /**
     * 市级名称
     */
    private String cityName;

    /**
     * 是否不限制区域
     */
    private Boolean isAllRegion;

    /**
     * 区域编码
     */
    private String regionCode;

    /**
     * 区域名称
     */
    private String regionName;

    public CommodityAreaResp(String provinceCode, String provinceName, String cityCode, String cityName, String regionCode, String regionName) {
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.regionCode = regionCode;
        this.regionName = regionName;
    }

}

package com.iuin.component.test.area;

import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;

public class TestDataGenerator {

    public static List<CommodityAreaResp> generateFlatAreaRespData() {
        List<CommodityAreaResp> flatAreas = new ArrayList<>();

        // 添加省级数据
        flatAreas.add(new CommodityAreaResp(
                "110000", "北京市", true, null, null, true, null, null
        ));
        flatAreas.add(new CommodityAreaResp(
                "310000", "上海市", true, null, null, true, null, null
        ));
        flatAreas.add(new CommodityAreaResp(
                "500000", "重庆市", true, null, null, true, null, null
        ));

        // 添加市级数据
        flatAreas.add(new CommodityAreaResp(
                "110000", "北京市", false, "110100", "北京市c", true, null, null
        ));
        flatAreas.add(new CommodityAreaResp(
                "310000", "上海市", false, "310100", "上海市c", true, null, null
        ));
        flatAreas.add(new CommodityAreaResp(
                "500000", "重庆市", false, "500100", "重庆市c", true, null, null
        ));

        // 添加区域数据
        flatAreas.add(new CommodityAreaResp(
                "110000", "北京市", false, "110100", "北京市c", false, "110101", "北京市东城区"
        ));
        flatAreas.add(new CommodityAreaResp(
                "310000", "上海市", false, "310100", "上海市c", false, "310101", "上海市黄浦区"
        ));
        flatAreas.add(new CommodityAreaResp(
                "500000", "重庆市", false, "500100", "重庆市c", false, "500101", "重庆市渝中区"
        ));

        return flatAreas;
    }

    public static List<CommoditySalesAreaTreeResp> generateTreeAreaRespData() {
        List<CommoditySalesAreaTreeResp> treeAreas = new ArrayList<>();

        // 添加根节点
        treeAreas.add(new CommoditySalesAreaTreeResp(
                "110000", "", "北京市", new ArrayList<>()
        ));
        treeAreas.add(new CommoditySalesAreaTreeResp(
                "310000", "", "上海市", new ArrayList<>()
        ));
        treeAreas.add(new CommoditySalesAreaTreeResp(
                "500000", "", "重庆市", new ArrayList<>()
        ));

        // 添加子节点
        treeAreas.add(new CommoditySalesAreaTreeResp(
                "110100", "110000", "北京市c", new ArrayList<>()
        ));
        treeAreas.add(new CommoditySalesAreaTreeResp(
                "310100", "310000", "上海市c", new ArrayList<>()
        ));
        treeAreas.add(new CommoditySalesAreaTreeResp(
                "500100", "500000", "重庆市c", new ArrayList<>()
        ));

        // 添加孙节点
        treeAreas.add(new CommoditySalesAreaTreeResp(
                "110101", "110100", "北京市东城区", new ArrayList<>()
        ));
        treeAreas.add(new CommoditySalesAreaTreeResp(
                "310101", "310100", "上海市黄浦区", new ArrayList<>()
        ));
        treeAreas.add(new CommoditySalesAreaTreeResp(
                "500101", "500100", "重庆市渝中区", new ArrayList<>()
        ));

        return treeAreas;
    }


    public static void main(String[] args) {
        List<CommodityAreaResp> commodityAreaRespList = generateFlatAreaRespData();

        List<CommoditySalesAreaTreeResp> commoditySalesAreaTreeRespList = AreaConverter.flatToTree(commodityAreaRespList);

        System.out.println(JSONUtil.formatJsonStr(JSONUtil.toJsonStr(commoditySalesAreaTreeRespList)));
    }
}

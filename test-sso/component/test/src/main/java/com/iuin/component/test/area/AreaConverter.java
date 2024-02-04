package com.iuin.component.test.area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaConverter {

    public static List<CommoditySalesAreaTreeResp> flatToTree(List<CommodityAreaResp> flatAreas) {
        Map<String, CommoditySalesAreaTreeResp> idToTreeMap = new HashMap<>();
        List<CommoditySalesAreaTreeResp> rootNodes = new ArrayList<>();

        for (CommodityAreaResp flatArea : flatAreas) {
            CommoditySalesAreaTreeResp treeNode = new CommoditySalesAreaTreeResp(
                    flatArea.getProvinceCode(),
                    flatArea.getProvinceCode() == null ? null : "",
                    flatArea.getProvinceName(),
                    new ArrayList<>()
            );
            idToTreeMap.put(treeNode.getId(), treeNode);

            if (flatArea.getIsAllCity()) {
                // 如果不限制城市，直接添加到根节点
                rootNodes.add(treeNode);
            } else {
                // 否则，找到对应的省级节点
                CommoditySalesAreaTreeResp parent = idToTreeMap.get(flatArea.getProvinceCode());
                if (parent != null) {
                    parent.getChildren().add(treeNode);
                }
            }

            // 处理市级和区域节点
            if (!flatArea.getIsAllCity() && flatArea.getCityCode() != null) {
                CommoditySalesAreaTreeResp cityNode = new CommoditySalesAreaTreeResp(
                        flatArea.getCityCode(),
                        flatArea.getProvinceCode(),
                        flatArea.getCityName(),
                        new ArrayList<>()
                );
                idToTreeMap.put(cityNode.getId(), cityNode);
                CommoditySalesAreaTreeResp provinceNode = idToTreeMap.get(flatArea.getProvinceCode());
                if (provinceNode != null) {
                    provinceNode.getChildren().add(cityNode);
                }

                if (!flatArea.getIsAllRegion() && flatArea.getRegionCode() != null) {
                    CommoditySalesAreaTreeResp regionNode = new CommoditySalesAreaTreeResp(
                            flatArea.getRegionCode(),
                            flatArea.getCityCode(),
                            flatArea.getRegionName(),
                            new ArrayList<>()
                    );
                    idToTreeMap.put(regionNode.getId(), regionNode);
                    CommoditySalesAreaTreeResp cityNodeFound = idToTreeMap.get(flatArea.getCityCode());
                    if (cityNodeFound != null) {
                        cityNodeFound.getChildren().add(regionNode);
                    }
                }
            }
        }

        return rootNodes;
    }

    public static List<CommodityAreaResp> treeToFlat(List<CommoditySalesAreaTreeResp> treeNodes) {
        List<CommodityAreaResp> flatAreas = new ArrayList<>();
        treeToFlatRecursive(treeNodes, null, flatAreas);
        return flatAreas;
    }

    private static void treeToFlatRecursive(List<CommoditySalesAreaTreeResp> treeNodes, String parentCode, List<CommodityAreaResp> flatAreas) {
        for (CommoditySalesAreaTreeResp treeNode : treeNodes) {
            CommodityAreaResp flatArea = new CommodityAreaResp(
                    treeNode.getId(),
                    treeNode.getName(),
                    parentCode != null && parentCode.isEmpty(),
                    parentCode != null ? parentCode : treeNode.getId(),
                    treeNode.getName(),
                    true,
                    treeNode.getId(),
                    treeNode.getName()
            );
            flatAreas.add(flatArea);
            treeToFlatRecursive(treeNode.getChildren(), treeNode.getId(), flatAreas);
        }
    }

}

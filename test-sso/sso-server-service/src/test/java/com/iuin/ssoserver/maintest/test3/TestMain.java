package com.iuin.ssoserver.maintest.test3;

import com.iuin.ssoserver.maintest.test2.SkuIdPayIdDTO;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>TODO</p>
 *
 * @author fa
 */
public class TestMain {

    public static void main(String[] args) {
        List<SkuIdPayIdDTO> objects = new ArrayList<>();
        objects.add(new SkuIdPayIdDTO());
        Long skuId = objects.stream().map(SkuIdPayIdDTO::getSkuId).filter(Objects::nonNull).findFirst().orElse(0L);

        System.out.println(skuId);
    }

}

package com.iuin.commodity.repostory.dao;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.iuin.commodity.repostory.entity.ComAddressDO;
import com.iuin.commodity.repostory.mapper.ComAddressMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author fa
 */
@Repository
public class ComAddressDao extends MPJBaseServiceImpl<ComAddressMapper, ComAddressDO> {

    public void saveTrans(ComAddressDO comAddressDO) {
        this.save(comAddressDO);
    }
}





package com.iuin.shardingsphere.repostory.dao;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.iuin.shardingsphere.repostory.entity.ComAddressDO;
import com.iuin.shardingsphere.repostory.mapper.ComAddressMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fa
 */
@Repository
public class ComAddressDao extends MPJBaseServiceImpl<ComAddressMapper, ComAddressDO> {

    public void saveTrans(ComAddressDO comAddressDO) {
        this.save(comAddressDO);
    }
}





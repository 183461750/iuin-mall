package com.iuin.commodity.repostory.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iuin.commodity.repostory.entity.ComAddressDO;
import com.iuin.commodity.repostory.mapper.ComAddressMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fa
 */
@Repository
public class ComAddressDao extends ServiceImpl<ComAddressMapper, ComAddressDO> {

    public void saveTrans(ComAddressDO comAddressDO) {
        this.save(comAddressDO);
    }
}





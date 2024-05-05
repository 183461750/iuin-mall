package com.iuin.commodity.repostory.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iuin.commodity.repostory.entity.ComUserDO;
import com.iuin.commodity.repostory.mapper.ComUserMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fa
 */
@Repository
public class ComUserDao extends ServiceImpl<ComUserMapper, ComUserDO> {

    public void saveTrans(ComUserDO comUserDO) {
        this.save(comUserDO);
    }
}





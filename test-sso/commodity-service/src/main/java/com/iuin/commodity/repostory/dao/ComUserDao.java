package com.iuin.commodity.repostory.dao;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.iuin.commodity.repostory.entity.ComUserDO;
import com.iuin.commodity.repostory.mapper.ComUserMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author fa
 */
@Repository
public class ComUserDao extends MPJBaseServiceImpl<ComUserMapper, ComUserDO> {

    public void saveTrans(ComUserDO comUserDO) {
        this.save(comUserDO);
    }
}





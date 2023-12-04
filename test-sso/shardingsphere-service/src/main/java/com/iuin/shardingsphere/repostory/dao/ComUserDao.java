package com.iuin.shardingsphere.repostory.dao;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.iuin.shardingsphere.repostory.entity.ComUserDO;
import com.iuin.shardingsphere.repostory.mapper.ComUserMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fa
 */
@Repository
public class ComUserDao extends MPJBaseServiceImpl<ComUserMapper, ComUserDO> {

    public void saveTrans(ComUserDO comUserDO) {
        this.save(comUserDO);
    }
}





package com.iuin.commodity.repostory.dao;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.iuin.commodity.repostory.entity.ComUserDO;
import com.iuin.commodity.repostory.mapper.ComUserDeepMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author fa
 */
@Repository
public class ComUserDeepDao extends MPJBaseServiceImpl<ComUserDeepMapper, ComUserDO.ComUserDeepDO> {

    public void saveTrans(ComUserDO.ComUserDeepDO comUserDeepDO) {
        this.save(comUserDeepDO);
    }
}





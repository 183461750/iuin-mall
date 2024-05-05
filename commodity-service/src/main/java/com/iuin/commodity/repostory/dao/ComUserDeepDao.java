package com.iuin.commodity.repostory.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iuin.commodity.repostory.entity.ComUserDO;
import com.iuin.commodity.repostory.mapper.ComUserDeepMapper;
import org.springframework.stereotype.Repository;

/**
 * @author fa
 */
@Repository
public class ComUserDeepDao extends ServiceImpl<ComUserDeepMapper, ComUserDO.ComUserDeepDO> {

    public void saveTrans(ComUserDO.ComUserDeepDO comUserDeepDO) {
        this.save(comUserDeepDO);
    }
}





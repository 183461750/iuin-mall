package com.iuin.shardingsphere.repostory.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iuin.shardingsphere.repostory.entity.ComUserDO;
import com.iuin.shardingsphere.repostory.mapper.ComUserDeepMapper;
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





package com.iuin.shardingsphere.service.impl;
import com.iuin.shardingsphere.repostory.dao.ComUserDeepDao;
import com.iuin.shardingsphere.repostory.entity.ComUserDO.ComUserDeepDO;
import com.google.common.collect.Lists;

import com.iuin.shardingsphere.repostory.dao.ComAddressDao;
import com.iuin.shardingsphere.repostory.dao.ComUserDao;
import com.iuin.shardingsphere.repostory.entity.ComAddressDO;
import com.iuin.shardingsphere.repostory.entity.ComUserDO;
import com.iuin.shardingsphere.service.ComUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fa
 */
@Service
@RequiredArgsConstructor
public class ComUserServiceImpl implements ComUserService {

    private final ComUserDao comUserDao;
    private final ComUserDeepDao comUserDeepDao;
    private final ComAddressDao comAddressDao;

    @Override
    @Transactional
    public void save() {
        ComUserDO.ComUserDeepDO comUserDeepDO = new ComUserDO.ComUserDeepDO();
        comUserDeepDO.setPid(1L);
        comUserDeepDO.setParentUser(new ComUserDeepDO());
        comUserDeepDO.setChildUser(Lists.newArrayList());
        comUserDeepDO.setChildUserCondition(Lists.newArrayList());
        comUserDeepDO.setAddressList(Lists.newArrayList());
        comUserDeepDO.setChildIds(Lists.newArrayList());
        comUserDeepDO.setId(0L);
        comUserDeepDO.setName("");
        comUserDeepDO.setAge(0);
        comUserDeepDO.setSex(0);
        comUserDeepDO.setEmail("");

        comUserDeepDao.saveTrans(comUserDeepDO);

        if (1 == 1) {
            throw new IllegalStateException("报错了");
        }

        ComAddressDO comAddressDO = new ComAddressDO();
        comAddressDO.setUserId(0L);
        comAddressDO.setCity("");
        comAddressDO.setAddress("");

        comAddressDao.saveTrans(comAddressDO);

    }

}





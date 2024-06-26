package com.iuin.shardingsphere.controller;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.iuin.shardingsphere.repostory.entity.ComAddressDO;
import com.iuin.shardingsphere.repostory.entity.ComUserDO;
import com.iuin.shardingsphere.dto.UserDTO;
import com.iuin.shardingsphere.repostory.mapper.ComUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SampleTest {

    @Autowired
    private ComUserMapper comUserMapper;

    @Test
    public void testSelect() {
        MPJLambdaWrapper<ComUserDO> wrapper = new MPJLambdaWrapper<ComUserDO>()
                .selectAll(ComUserDO.class)//查询user表全部字段
                .select(ComAddressDO::getCity, ComAddressDO::getAddress)
                .leftJoin(ComAddressDO.class, ComAddressDO::getUserId, ComUserDO::getId);

        List<UserDTO> userList = comUserMapper.selectJoinList(UserDTO.class, wrapper);

        userList.forEach(System.out::println);
    }

}
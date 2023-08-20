package com.iuin.commodity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iuin.commodity.domain.ComUserDO;
import com.iuin.commodity.mapper.ComUserMapper;
import com.iuin.commodity.service.ComUserService;
import org.springframework.stereotype.Service;

/**
* @author fa
* @description 针对表【com_user】的数据库操作Service实现
* @createDate 2023-08-20 22:09:43
*/
@Service
public class ComUserServiceImpl extends ServiceImpl<ComUserMapper, ComUserDO>
    implements ComUserService {

}





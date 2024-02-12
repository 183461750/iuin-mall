package com.iuin.component.test.mysql.service.impl;

import com.iuin.component.test.mysql.repostory.dao.SubMerchantIdCustomersPrizesDao;
import com.iuin.component.test.mysql.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author fa
 */
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {


    private final SubMerchantIdCustomersPrizesDao subMerchantIdCustomersPrizesDao;

    @Override
    public Boolean insertDate() {
        return null;
    }

}

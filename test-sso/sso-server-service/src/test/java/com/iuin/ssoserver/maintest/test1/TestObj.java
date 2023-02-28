package com.iuin.ssoserver.maintest.test1;

import com.iuin.ssoserver.maintest.annotations.OperateLogParamBillId;

/**
 * <p>TODO</p>
 *
 * @author fa
 */
public class TestObj {

    @OperateLogParamBillId
    private Long dataId;

    public TestObj() {
    }

    public TestObj(Long dataId) {
        this.dataId = dataId;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }
}

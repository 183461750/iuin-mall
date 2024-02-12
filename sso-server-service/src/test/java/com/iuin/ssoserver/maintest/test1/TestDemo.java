package com.iuin.ssoserver.maintest.test1;

import com.iuin.ssoserver.maintest.annotations.OperateLogParamList;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author fa
 */
public class TestDemo {

    @OperateLogParamList
    List<TestObj> objList;

    public TestDemo() {
    }

    public TestDemo(List<TestObj> objList) {
        this.objList = objList;
    }

    public List<TestObj> getObjList() {
        return objList;
    }

    public void setObjList(List<TestObj> objList) {
        this.objList = objList;
    }
}

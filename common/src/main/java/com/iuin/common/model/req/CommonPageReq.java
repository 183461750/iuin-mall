package com.iuin.common.model.req;

import lombok.Data;

/**
 * @author fa
 */
@Data
public class CommonPageReq {

    /**
     * 当前页
     */
    private int current;

    /**
     * 每页行数
     */
    private int pageSize;

    public CommonPageReq() {
        this.current = 1;
        this.pageSize = 10;
    }

}

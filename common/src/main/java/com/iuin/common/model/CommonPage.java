package com.iuin.common.model;

import lombok.Data;

/**
 * @author fa
 */
@Data
public class CommonPage {

    /**
     * 当前页
     */
    private int current;

    /**
     * 每页行数
     */
    private int pageSize;

    public CommonPage() {
        this.current = 1;
        this.pageSize = 10;
    }

}

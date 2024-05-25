package com.iuin.common.model.resp;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据响应对象
 *
 * @param <T> 数据体data
 */
@Setter
@Getter
@AllArgsConstructor
public class PageDataResp<T> {
    /**
     * 记录总条数
     */
    private Long totalCount;

    /**
     * 数据体
     */
    private List<T> data;

    public PageDataResp() {
        this.totalCount = 0L;
        this.data = new ArrayList<>();
    }

}

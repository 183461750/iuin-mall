package com.iuin.commodity.model.resp.req;

import com.iuin.commodity.repostory.entity.ComUserDO;
import lombok.Data;

/**
 * @author fa
 */
@Data
public class Test1Req {

    private String name;

    /**
     * 用户ID
     *
     * @see ComUserDO#getId()
     */
    private String userId;

}

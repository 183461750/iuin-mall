package com.iuin.component.test.jpa.materiel;

import com.iuin.common.utils.DefaultValueUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * 活动表
 *
 * @author Fa
 */
@Setter
@Getter
@ToString
@FieldNameConstants(asEnum = true)
public class ActivityDO {

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动类型
     */
    private Integer type;

    /**
     * 活动开始时间
     */
    protected LocalDateTime startDate;

    /**
     * 活动结束时间
     */
    private LocalDateTime endDate;

    /**
     * 状态
     */
    private Integer status;

    public void prePersist() {

        DefaultValueUtil.initDefaultData(this, Fields::values);

    }

}

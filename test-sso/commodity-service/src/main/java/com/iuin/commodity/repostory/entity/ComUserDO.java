package com.iuin.commodity.repostory.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.yulichang.annotation.Apply;
import com.github.yulichang.annotation.Condition;
import com.github.yulichang.annotation.EntityMapping;
import com.github.yulichang.annotation.FieldMapping;
import com.github.yulichang.annotation.enums.SqlKeyword;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author fa
 */
@TableName(value = "com_user")
@Data
@FieldNameConstants
public class ComUserDO implements Serializable {

    @TableId
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 邮箱
     */
    private String email;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Data
    @FieldNameConstants
    @EqualsAndHashCode(callSuper = true)
    @TableName(value = "com_user")
    public static class ComUserDeepDO extends ComUserDO {

        @TableId
        private Long id;
        private Long pid;//父id
        /* 其他属性略 */

        /**
         * 查询上级 一对一
         */
        @TableField(exist = false)
        @EntityMapping(thisField = Fields.pid, joinField = Fields.id)
        private ComUserDeepDO parentUser;

        /**
         * 查询下级 一对多
         */
        @TableField(exist = false)
        @EntityMapping(thisField = Fields.id, joinField = Fields.pid)
        private List<ComUserDeepDO> childUser;

        /**
         * 带条件的查询下级 一对多
         */
        @TableField(exist = false)
        @EntityMapping(thisField = Fields.id, joinField = Fields.pid,
                condition = {
                        //sex = '0' 默认条件是等于
                        @Condition(column = ComUserDO.Fields.sex, value = "0"),
                        //name like '%a%'
                        @Condition(column = ComUserDO.Fields.name, value = "张三", keyWord = SqlKeyword.LIKE)
                },
                apply = @Apply(value = "id between 1 and 20"))//拼接sql 同 wrapper.apply()
        private List<ComUserDeepDO> childUserCondition;

        /**
         * 查询地址 (一对多)
         */
        @TableField(exist = false)
        @EntityMapping(thisField = Fields.id, joinField = ComAddressDO.Fields.userId)
        private List<ComAddressDO> addressList;

        /**
         * 绑定字段 （一对多）
         */
        @TableField(exist = false)
        @FieldMapping(tag = ComUserDeepDO.class, thisField = Fields.id, joinField = Fields.pid, select = Fields.id)
        private List<Integer> childIds;
    }

}
package com.iuin.component.yapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * yapi展示分组信息返回对象
 * @author whm
 * @version 2.0.0
 * @since 2023-04-13
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListGroupResp implements Serializable {
    private static final long serialVersionUID = 1615664980633823878L;

    /**
     * {
     *   "custom_field1": {
     *     "enable": false
     *   },
     *   "type": "private",
     *   "_id": 345,
     *   "group_name": "个人空间",
     *   "add_time": 1637715779,
     *   "up_time": 1637715779,
     *   "role": "owner"
     * }
     */

    private Map<String, ?> custom_field1;
    private String type;
    private String _id;
    private String group_name;
    private String add_time;
    private String up_time;
    private String role;
}

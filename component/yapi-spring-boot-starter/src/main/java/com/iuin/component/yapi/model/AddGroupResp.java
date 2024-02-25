package com.iuin.component.yapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * yapi新增分组返回信息
 * @author whm
 * @version 3.0.0
 * @since 2023-04-13
 **/
@NoArgsConstructor
@Data
public class AddGroupResp {

    /**
     * uid : 62
     * group_name : test1
     * members : [{"uid":62,"role":"owner","_id":"60f684eeda7c0a33db618a72","email":"yaozicong_lmk@163.com","username":"yzc"}]
     * group_desc : 测试描述
     * _id : 289
     * type : public
     */

    private String add_time;
    private String group_name;
    private String role;
    private Integer type;
    private String up_time;
    private String _id;
    private List<MembersBean> custom_field1;

    @NoArgsConstructor
    @Data
    public static class MembersBean {
        /**
         * uid : 62
         * role : owner
         * _id : 60f684eeda7c0a33db618a72
         * email : yaozicong_lmk@163.com
         * username : yzc
         */

        private Integer uid;
        private String role;
        private String _id;
        private String email;
        private String username;
    }
}

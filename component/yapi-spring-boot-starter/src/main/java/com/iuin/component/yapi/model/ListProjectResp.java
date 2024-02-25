package com.iuin.component.yapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * yapi展示分组内的项目信息返回对象
 *
 * @author whm
 * @version 2.0.0
 * @since 2023-04-13
 **/
@NoArgsConstructor
@Data
public class ListProjectResp implements Serializable {
    private static final long serialVersionUID = -2579122852601983334L;

    /**
     * project_type : private
     * color : yellow
     * basepath :
     * icon : code-o
     * env : [{"domain":"http://127.0.0.1","name":"local","header":[],"global":[],"_id":"60dbe2e56f287913403e8bff"}]
     * follow : false
     * switch_notice : true
     * uid : 11
     * group_id : 191
     * name : 搜索服务
     * up_time : 1626764230
     * _id : 510
     * add_time : 1625023205
     */

    private String project_type;
    private String color;
    private String basepath;
    private String icon;
    private String follow;
    private String switch_notice;
    private String uid;
    private String group_id;
    private String name;
    private String up_time;
    private String _id;
    private String add_time;
    private List<EnvBean> env;

    @NoArgsConstructor
    @Data
    public static class EnvBean {
        /**
         * domain : http://127.0.0.1
         * name : local
         * header : []
         * global : []
         * _id : 60dbe2e56f287913403e8bff
         */

        private String domain;
        private String name;
        private String _id;
        private List<?> header;
        private List<?> global;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Tuple {
        private String projectId;
        private String projectName;
    }
}

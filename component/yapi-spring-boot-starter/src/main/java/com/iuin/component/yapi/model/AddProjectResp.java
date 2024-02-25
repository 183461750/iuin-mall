package com.iuin.component.yapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * yapi新增项目返回信息
 * @author whm
 * @version 3.0.0
 * @since 2023-04-13
 **/
@NoArgsConstructor
@Data
public class AddProjectResp {

    private String switch_notice;
    private String is_mock_open;
    private String strice;
    private String is_json5;
    private String name;
    private String basepath;
    private String project_type;
    private String uid;
    private String group_id;
    private String icon;
    private String color;
    private String add_time;
    private String up_time;
    private String _id;
    private String __v;
    private List<?> members;
    private List<EnvBean> env;
    private List<?> tag;

    @NoArgsConstructor
    @Data
    public static class EnvBean {
        /**
         * header : []
         * _id : 60f68837da7c0a33db618a73
         * name : local
         * domain : http://127.0.0.1
         * global : []
         */

        private String _id;
        private String name;
        private String domain;
        private List<?> header;
        private List<?> global;
    }
}

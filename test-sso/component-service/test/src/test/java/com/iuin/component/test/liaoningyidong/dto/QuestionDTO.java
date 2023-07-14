package com.iuin.component.test.liaoningyidong.dto;

import lombok.Data;

import java.util.List;


/**
 * 题目的类型操作
 */
@Data
public class QuestionDTO {

    //单选 or 多选  radio select
    private String type;
    //题目类型 ：text choice circle
    private String qtype;
    //题目内容
    private String title;
    //题目的唯一标识
    private String id;

    //答案的列表
    /**
     * 结构：
     * {
     * value:"xxx",
     * image:"xxxx"
     * }
     * <p>
     * 这里需要测试 : JSON str   --->  JSON obj
     * JSONObject.parseObject(str, QuestionDTO.class)  这里测试是成功的
     */
    private List<QResult> answers;

    public QuestionDTO() {
    }
}

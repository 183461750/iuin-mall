package com.iuin.component.test.liaoningyidong.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iuin.component.test.liaoningyidong.dto.AnswerDTO;
import com.iuin.component.test.liaoningyidong.dto.QuestionDTO;
import com.iuin.component.test.liaoningyidong.utils.Tools;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>TODO</p>
 *
 * @author fa
 */
public class Test1 {

    public static void main(String[] args) {
        String jsonStr = "{\"question\":[{\"qtype\":\"text\",\"id\":1685693878485,\"title\":\"为您身边的考生送上属于您的高考祝福\"}],\"isShowLottery\":true}";
        JSONObject configObj = JSON.parseObject(jsonStr);
        JSONArray questionsArray = Tools.objectToJson(configObj.get("question"), JSONArray.class);
        List<QuestionDTO> collect = questionsArray.stream().map(x -> {
            return Tools.objectToJson(x, QuestionDTO.class);
        }).collect(Collectors.toList());

        System.out.println(collect);

        List<Boolean> text = collect.stream().map(QuestionDTO::getQtype).map(s -> Objects.equals("text", s)).collect(Collectors.toList());
        System.out.println(text);


        List<QuestionDTO> collect1 = questionsArray.stream().map(x -> {
            return Tools.objectToJson(x, QuestionDTO.class);
        }).collect(Collectors.toList());

        String dataJsonStr = "[{\"index\":1,\"id\":1685693878485,\"value\":\"金榜题名\"}]";

        List<AnswerDTO> answerList = JSONObject.parseArray(dataJsonStr, AnswerDTO.class);

        List<List<Boolean>> collect2 = answerList.stream().map(oneAnswer -> {
            return collect1.stream().map(oneQuestion -> {
                System.out.println(!Objects.isNull(oneQuestion.getId()) && Objects.equals(oneQuestion.getId(), oneAnswer.getId()));
                return !Objects.isNull(oneQuestion.getId()) && Objects.equals(oneQuestion.getId(), oneAnswer.getId());
            }).collect(Collectors.toList());
        }).collect(Collectors.toList());

        System.out.println(collect2);

        System.out.println();
    }

}

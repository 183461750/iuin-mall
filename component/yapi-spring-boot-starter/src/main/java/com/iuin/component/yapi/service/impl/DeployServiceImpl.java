package com.iuin.component.yapi.service.impl;

import cn.hutool.core.text.StrPool;
import com.iuin.common.constants.ProjectConstant;
import com.iuin.common.enums.ModuleEnum;
import com.iuin.common.utils.resp.ResponseCode;
import com.iuin.component.base.exceptions.BusinessException;
import com.iuin.component.yapi.model.ListGroupResp;
import com.iuin.component.yapi.model.ListProjectResp;
import com.iuin.component.yapi.model.TupleResp;
import com.iuin.component.yapi.model.YapiTokenListResp;
import com.iuin.component.yapi.service.IDeployService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 项目部署
 *
 * @author ds
 * @version 2.0.0
 * @since 2023/1/13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeployServiceImpl implements IDeployService {

    private final YapiServiceImpl yapiService;
    private final HttpServletResponse httpServletResponse;

    /**
     * 主模块名称
     */
    private static final String MAIN_NAME = "main";

    @Override
    public List<YapiTokenListResp> listOfYapiToken() {
        try {
            // 校验配置
            yapiService.checkInitYapiConfig();

            // 登录
            yapiService.yapiLogin();

            return yapiService.listProject(yapiService.getGroupId())
                    .stream()
                    .map(projectResponse -> yapiService.listProjectTokens(projectResponse.get_id(), projectResponse.getName()))
                    .collect(Collectors.toList());
        } finally {
            AbstractYapiService.cookieHolder.remove();
        }
    }

    /**
     * 1.登录
     * 2.获取所有分组信息
     * 3.获取当前分组内的项目列表，创建名称不存在的那些项目
     * 4.获取当前分组内的所有项目的token
     * 5.将结果转成文件流供客户端下载（格式：服务名=token）
     *
     * @see ModuleEnum
     */
    @Override
    public void initYapiProject() {
        try {
            doInitYapiProject();
        } finally {
            AbstractYapiService.cookieHolder.remove();
        }
    }

    /**
     * 1.登录
     * 2.获取所有分组信息，如果名称已经存在则获取该分组，不存则创建
     * 3.获取当前分组内的项目列表，创建名称不存在的那些项目
     * 4.获取当前分组内的所有项目的token
     * 5.将结果转成文件流供客户端下载（格式：服务名=token）
     *
     * @see ModuleEnum
     */
    @Override
    public void initByGroupName(String groupName) {
        try {
            doInitYapiProjectByGroupName(groupName);
        } finally {
            AbstractYapiService.cookieHolder.remove();
        }
    }

    private String doInitYapiProject() {
        // 校验配置
        yapiService.checkInitYapiConfig();

        // 登录
        yapiService.yapiLogin();

        return doInitYapiProjectByGroupId(yapiService.getGroupId());
    }

    private String doInitYapiProjectByGroupName(String groupName) {
        // 校验配置
        yapiService.checkInitYapiConfig();

        // 登录
        yapiService.yapiLogin();

        // 获取yapi所有分组信息
        ListGroupResp listGroupResponse = yapiService.listGroup()
                .stream()
                .filter(groupResponse -> Objects.equals(groupResponse.getGroup_name(), groupName))
                .findAny()
                .orElse(null);

        // 如果存在该名称的分组，则直接获取groupId
        // 如果不存在该名称的分组，则创建一个分组，并获取groupId
        String groupId = Optional.ofNullable(listGroupResponse)
                .map(ListGroupResp::get_id)
                .orElseGet(() -> yapiService.addGroup(groupName, groupName).get_id());

        return doInitYapiProjectByGroupId(groupId);
    }

    private String doInitYapiProjectByGroupId(String groupId) {
        // 获取当前分组内的项目列表，创建名称不存在的那些项目 DeployModuleEnum
        Map<String, ListProjectResp.Tuple> listProjectResponseTupleMap = yapiService.listProject(groupId)
                .stream()
                .map(listProjectResponse -> new ListProjectResp.Tuple(listProjectResponse.get_id(), listProjectResponse.getName()))
                .collect(Collectors.toMap(ListProjectResp.Tuple::getProjectName, Function.identity()));

        // 过滤出那些还未创建的项目名称
        List<ModuleEnum> waitCreatProjectNameList = Arrays.stream(ModuleEnum.values())
                .filter(deployModuleEnum -> !listProjectResponseTupleMap.containsKey(deployModuleEnum.getCnName()))
                .collect(Collectors.toList());

        // 当前的项目集合
        List<ListProjectResp.Tuple> projectTupleList = Stream.concat(
                listProjectResponseTupleMap.values().stream()
                , waitCreatProjectNameList.stream()
                        .map(deployModuleEnum -> yapiService.addProject(groupId, deployModuleEnum.getCnName()))
                        .map(listProjectResponse -> new ListProjectResp.Tuple(listProjectResponse.get_id(), listProjectResponse.getName()))
        ).collect(Collectors.toList());

        // 获取所有项目的token列表<Name:Token>
        Map<String, String> projectTokenMap = projectTupleList.stream()
                .map(projectTuple -> yapiService.listProjectTokens(projectTuple.getProjectId(), projectTuple.getProjectName()))
                .collect(Collectors.toMap(YapiTokenListResp::getName, YapiTokenListResp::getToken));

        // 构造tokenMap
        Map<String, String> tokenMap = Arrays.stream(ModuleEnum.values())
                .map(deployModuleEnum -> new TupleResp<>(ProjectConstant.PROJECT_NAME + StrPool.C_DOT + deployModuleEnum.getName() + StrPool.C_DOT + MAIN_NAME, projectTokenMap.get(deployModuleEnum.getCnName())))
                .collect(Collectors.toMap(TupleResp::getT1, TupleResp::getT2));

        // 按tokenMap中的key进行排序（避免每次下载文件内容顺序不一致)
        tokenMap = tokenMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldVal, newVal) -> oldVal,
                LinkedHashMap::new
        ));

        // 生成yml串
        String resText = new Yaml().dumpAsMap(new EasyYapiExportModel(yapiService.getServerUrl(), tokenMap));

        // 输出文件流供外层下载
        httpServletResponse.setContentType("text/plain");

        try {
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(".easy.api.yml", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("yapi项目初始化,io输出异常:{},输出文本:{}", e.getMessage(), resText);
            throw new BusinessException(ResponseCode.MAN_SERVICE_PLATFORM_DEPLOY_YAPI_REQUEST_ERROR2);
        }

        try (ServletOutputStream outputStream = httpServletResponse.getOutputStream();
             BufferedOutputStream buffer = new BufferedOutputStream(outputStream)) {
            buffer.write(resText.getBytes(StandardCharsets.UTF_8));
            buffer.flush();
        } catch (IOException e) {
            log.error("yapi项目初始化,io输出异常:{},输出文本:{}", e.getMessage(), resText);
            throw new BusinessException(ResponseCode.MAN_SERVICE_PLATFORM_DEPLOY_YAPI_REQUEST_ERROR2);
        }

        return groupId;
    }

    @Data
    static class EasyYapiExportModel {
        private EasyYapiConfig yapi;

        public EasyYapiExportModel(String serverUrl, Map<String, String> tokenMap) {
            this.yapi = new EasyYapiConfig(serverUrl, tokenMap);
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        static class EasyYapiConfig {
            private String server;
            private Map<String, String> token;
        }
    }
}

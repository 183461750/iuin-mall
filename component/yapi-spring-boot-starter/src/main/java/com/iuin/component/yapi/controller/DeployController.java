package com.iuin.component.yapi.controller;

import com.iuin.common.utils.RespResult;
import com.iuin.component.yapi.model.YapiTokenListResp;
import com.iuin.component.yapi.service.IDeployService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * yapi项目部署
 *
 * @author Fa
 */
@RestController
@RequestMapping("/yapi/deploy")
@Validated
@RequiredArgsConstructor
public class DeployController {

    private final IDeployService deployService;

    /**
     * Yapi-查询项目相关的token列表
     */
    @GetMapping(value = "/yapi/token/list")
    public RespResult<List<YapiTokenListResp>> listOfYapiToken() {
        return RespResult.success(deployService.listOfYapiToken());
    }

    /**
     * Yapi-项目初始化（返回文件供下载，文件内容： <服务：token>）
     * 根据groupId进行初始化，先在yapi管理平台中建分组，然后把groupId存到nacos配置中
     */
    @GetMapping(value = "/yapi/init")
    public void initYapiProject() {
        deployService.initYapiProject();
    }

    /**
     * nacos没有提供客户端修改个别配置推送服务端的接口，只有重新发布配置的接口
     * 从nacos读取回来的配置是一个String，比较难处理成yml格式（且不一定是yml格式）
     * 使用yml工具类等，不好修改配置，且无法保持配置注释和换行等，直接处理string也不是很方便，因为时间有限，所以这个优化下次在做
     */
//    /**
//     * Yapi-项目初始化（返回文件供下载，文件内容： <服务：token>）
//     * 支持创建并初始化不存在分组
//     * 支持初始化已经存在的分组
//     * 直接更新最新的GroupId到nacos配置中心
//     */
//    @GetMapping(value = "/yapi/initByGroupName")
//    public void initByGroupName(@RequestParam("groupName") @NotBlank(message = "分组名称不能为空") String groupName) {
//        deployService.initByGroupName(groupName);
//    }
}

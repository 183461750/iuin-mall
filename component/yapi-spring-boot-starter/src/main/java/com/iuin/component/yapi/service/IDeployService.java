package com.iuin.component.yapi.service;


import com.iuin.component.yapi.model.YapiTokenListResp;

import java.util.List;

/**
 * yapi项目部署Service
 * @author whm
 * @version 3.0.0
 * @since 2023/4/14
 */
public interface IDeployService {
    List<YapiTokenListResp> listOfYapiToken();

    void initYapiProject();

    void initByGroupName(String groupName);
}

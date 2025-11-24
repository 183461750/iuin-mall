package com.iuin.commodity.service;

import cn.hutool.core.bean.BeanUtil;
import com.iuin.commodity.model.resp.Test1Resp;
import com.iuin.commodity.model.resp.req.Test1Req;
import com.iuin.commodity.repostory.dao.ComUserDao;
import com.iuin.commodity.repostory.entity.ComUserDO;
import com.iuin.common.utils.RespResult;
import com.iuin.common.utils.RespUtil;
import com.iuin.search.api.feign.CategoryFeign;
import com.iuin.search.api.model.req.CategoryReq;
import com.iuin.search.api.model.resp.CategoryResp;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author fa
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements ITestService {

    private final ComUserDao comUserDao;
    private final CategoryFeign categoryFeign;

    @Resource(name = "commodityAsyncThreadPoolExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public Test1Resp test1(Test1Req req) {
        // 解决多线程情况下多语言配置失效问题
        LocaleContextHolder.setLocaleContext(LocaleContextHolder.getLocaleContext(), true);

        CompletableFuture<ComUserDO> ComUserDOFuture = CompletableFuture.supplyAsync(() -> comUserDao.getById(req.getUserId()), threadPoolTaskExecutor);

        testAsync(req);

        ComUserDOFuture.join();

        RespResult<CategoryResp> respResult = categoryFeign.info(CategoryReq.builder().name(req.getName()).build());
        CategoryResp categoryResp = RespUtil.getDataOrThrow(respResult);
        return BeanUtil.copyProperties(categoryResp, Test1Resp.class);
    }

    @Async("commodityAsyncThreadPoolExecutor")
    public void testAsync(Test1Req req) {
        ComUserDO comUserDO = comUserDao.getById(req.getUserId());
        log.info("异步线程: {}", comUserDO);
    }

}

package com.iuin.search.controller;

import com.iuin.common.utils.RespResult;
import com.iuin.search.model.TestAddReq;
import com.iuin.search.model.TestGetReq;
import com.iuin.search.entity.es.CommodityEs;
import com.iuin.search.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author fa
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService testService;

    @PostMapping("/add")
    public RespResult<CommodityEs> add(@Valid TestAddReq req) {
        return RespResult.success(testService.add(req));
    }

    @GetMapping("/get")
    public RespResult<String> get(@Valid TestGetReq testGetReq) {
        return RespResult.success(testService.get(testGetReq));
    }

}

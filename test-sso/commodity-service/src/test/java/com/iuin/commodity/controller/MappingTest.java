package com.iuin.commodity.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iuin.commodity.repostory.entity.ComUserDO;
import com.iuin.commodity.service.ComUserDeepService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 一对一，一对多关系映射查询
 * 以Deep结尾的方法会进行映射查询
 * 如果不需要关系映射就使用mybatis plus原生方法即可，比如 getById listByIds 等
 * <p>
 * 注意：关系映射不会去关联查询，而是执行多次单表查询（对结果汇总后使用in语句查询,再对结果进行匹配）
 */
@SpringBootTest
class MappingTest {

    @Resource
    private ComUserDeepService comUserDeepService;

    @Test
    void test1() {
        ComUserDO.ComUserDeepDO uesr = comUserDeepService.getByIdDeep(2);
        System.out.println(uesr);
    }

    @Test
    void test2() {
        List<ComUserDO.ComUserDeepDO> list = comUserDeepService.listDeep(Wrappers.emptyWrapper());
        list.forEach(System.out::println);
    }

    @Test
    void test3() {
        Page<ComUserDO.ComUserDeepDO> page = new Page<>(2, 2);
        Page<ComUserDO.ComUserDeepDO> result = comUserDeepService.pageDeep(page, Wrappers.emptyWrapper());
        result.getRecords().forEach(System.out::println);
    }
}
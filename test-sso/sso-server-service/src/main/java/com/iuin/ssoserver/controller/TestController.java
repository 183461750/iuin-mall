package com.iuin.ssoserver.controller;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sso.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.iuin.ssoserver.entity.RoleDO;
import com.iuin.ssoserver.entity.RoleDO_;
import com.iuin.ssoserver.entity.StudentDO;
import com.iuin.ssoserver.entity.StudentDO_;
import com.iuin.ssoserver.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * test-controller
 * @author fa
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private StudentRepository studentRepository;

    @GetMapping("/demo")
    public HashMap<String, Object> demo(String test) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("1", 1);
        map.put("test", test);
        return map;
    }

    @GetMapping("/demo2")
    public HashMap<String, Object> demo2(String test) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("1", 1);
        map.put("test", test);
        log.info("test: {}", test);
        return map;
    }


    // 获取指定用户的关注列表
    @RequestMapping("/sso/getFollowList")
    public Object ssoRequest(Long loginId) {

        StpUtil.checkLogin();

        // 校验签名，签名不通过直接抛出异常
        SaSsoUtil.checkSign(SaHolder.getRequest());

        // 查询数据 (此处仅做模拟)
        List<Integer> list = Arrays.asList(10041, 10042, 10043, 10044);

        // 返回
        return SaResult.data(list);
    }


    @GetMapping("/getData")
    public Object getData(Boolean deleted, String roleName) {
        return studentRepository.findAll(
                (root, query, criteriaBuilder) -> {
                    Join<StudentDO, RoleDO> roleJoin = root.join(StudentDO_.roleDOList, JoinType.LEFT);
                    return criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(StudentDO_.DELETED), deleted),
                            criteriaBuilder.like(roleJoin.get(RoleDO_.name), "%" + roleName + "%")
                    );
                }
        );
    }

}

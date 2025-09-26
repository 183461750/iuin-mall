package com.iuin.component.jpa.test;

import com.iuin.component.jpa.annotations.ConditionalOnSubclass;

/**
 * 测试类
 *
 * @author Fa
 */
// 检查是否存在子类(包括继承和实现)
@ConditionalOnSubclass(TestConfig.class)
public class TestConfig {

}

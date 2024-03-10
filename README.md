# 项目说明文档

商城相关系统, 主要是做技术沉淀, 不考虑业务逻辑.

## 领域类相关说明

- xxxPD
  公共领域(public domain), 以前也叫领域模型(domain model), 用于表示业务领域中的概念和实体。
  后来发现领域模型的概念不太适合, 因为在DDD中, 更多的是把它领域模型当作实体来使用
  所以, 为了防止概念冲突, 把DM改名为PD, 即公共领域.
  OL(Ordinary Logic)/GL(Generic Logic)/UL(Universal Logic)/BD(business domain)

## 有趣实用小功能

- 枚举相关
  - 通过枚举去除if(由配置文件配了一系列列表数据, 正常需要if去判断具体是列表中的哪个值)
    - 读取application.yml变量, 通过枚举绑定对应的配置对象的成员变量和常量去消除if
      - 有点像, 另类的策略模式
      - 示例代码: `com.iuin.workflow.common.enums.ProcessDefEnum`

## TODO

### 未来的迭代方向

- 调整代码模块结构
  - 将现有的service模块改成app模块
    - 并将app模块分为支撑模块(support-app)和业务模块(biz-app)
      - 将每一个业务模块的子模块(例如biz-app下的commodity-app子模块)拆分为多个模块
        - 好处是, 可以抽离出公共模块, 给多个commodity-app下的子app进行共用
          - 例如, commodity-app下有commodity-api-app、commodity-job-app、commodity-service和commodity-domain子模块
            - 依赖关系: commodity-service依赖commodity-domain, commodity-api-app依赖commodity-service, commodity-job-app依赖commodity-service
- 接入redis cluster
- 接入arthas tunnel
  - 包装一层到支撑app中去

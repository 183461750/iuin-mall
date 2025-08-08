# base-spring-boot-starter

## 关于logback配置文件说明

```yml
# 配置到nacos的common.yml文件汇总
logging:
  # 目的: 切换log写入磁盘和写入skywalking的功能, 以及解决xml读取时机太早导致启动日志会有XXX_IS_UNDEFINED报错问题: https://blog.csdn.net/a405828/article/details/136182791?ops_request_misc=&request_id=&biz_id=102
  config: classpath:logback-skywalking.xml
```

## 安全调用功能说明

### 功能介绍
提供[@SafeCall](../component/base/aspect/SafeCallAspect.java#L23-L23)注解和SafeCallAspect切面，用于包装指定方法，捕获方法执行过程中抛出的异常并记录日志，同时不影响主流程的执行。

### 使用方法
1. 在需要安全调用的方法上添加[@SafeCall](../component/base/aspect/SafeCallAspect.java#L23-L23)注解
2. 可以通过value属性添加异常描述信息
3. 可以通过stackTrace属性控制是否记录异常堆栈信息

### 示例代码
```java
@SafeCall(value = "发送短信通知", stackTrace = true)
public void sendSmsForOrderNotPaidAsync(OrderDO orderDO, MemberFeignCodeRes memberFeignCodeRes) {
    // 发送短信的具体实现
    // 即使这里抛出异常，也不会影响调用方的执行流程
}
```

当被注解的方法抛出异常时，会记录类似以下格式的日志：
```
[SafeCall] 方法执行异常 - 类名: SmsComponent, 方法名: sendSmsForOrderNotPaidAsync, 描述: 发送短信通知, 异常信息: 连接超时
```
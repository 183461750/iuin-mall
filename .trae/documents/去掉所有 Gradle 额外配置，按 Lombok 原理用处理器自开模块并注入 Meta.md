## 目标
- 不改动 `temp/lombok`，不使用 Agent/插件，不增加任何 Gradle JVM 参数。
- 在组件内用 JSR-269 注解处理器，严格按 Lombok 的“编译期 AST 注入”逻辑，在类体内生成 `Meta`，仅包含 `SIMPLE_CLASS_NAME` 与 `CLASS_NAME`。

## 关键实现（对齐 Lombok 精髓）
- 在处理器初始化阶段（`init`）实现“自开模块”，参考 Lombok `addOpensForLombok`：
  - 反射获取 `ModuleLayer.boot()` → `jdk.compiler` 模块；反射获取“当前未命名模块”。
  - 利用 `sun.misc.Unsafe` 将 `java.lang.Module` 私有方法 `implAddOpens` 可访问，依次对 `com.sun.tools.javac.{api,processing,tree,util,code,comp,parser,main,model,file,jvm}` 执行 opens 到未命名模块。
  - 同步禁用 `jdk.internal.module.IllegalAccessLogger` 以避免 JDK9+ 反射警告（参考 Lombok `AnnotationProcessor.disableJava9SillyWarning`）。
- 处理器注入逻辑：
  - 删除注解节点（仅编译期用）。
  - 校验：仅允许顶层类或静态嵌套类。
  - 在 `JCClassDecl` 里注入 `public static final class Meta`、私有构造。
  - 注入 `SIMPLE_CLASS_NAME` 与 `CLASS_NAME` 常量（类名用 `ElementUtils.getBinaryName`，含 `$`）。
  - 做成员存在性检查、轻量 generated 标记。

## 变更范围
- 增加处理器源码（组件内）并注册 `META-INF/services/javax.annotation.processing.Processor`。
- 移除此前我加的所有 Gradle 额外参数改动（根 + 各模块），保持原来干净配置。
- 保留之前添加的 Lombok SPI Handler文件仅作参考用途（如你希望保留）；最终实现以 JSR-269 处理器为准。

## 验证
- 构建 `:component:pluggable-annotation:compile-time-code-gen`。
- 构建 `:component:test`，验证：
  - `NormalClass.Meta.SIMPLE_CLASS_NAME/CLASS_NAME`
  - `Outer.Inner.Meta.CLASS_NAME`（包含 `$`）。
- IDE 仅需开启注解处理，无需任何 JVM 参数。

## 交付
- 提交处理器与模块自开实现、服务清单；
- 清理所有多余 Gradle 参数；
- 编译日志与示例验证结果。
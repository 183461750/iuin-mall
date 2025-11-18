## 目标
- 不改动 `temp/lombok`（仅作参考）。
- 在你的自定义组件中按 Lombok 的逻辑实现“编译期在当前类体注入内部类”，默认内部类名 `Meta`，仅生成 `SIMPLE_CLASS_NAME` 与 `CLASS_NAME`。
- 微服务模块无需逐模块配置编译参数；统一在根工程一次性配置，使整体使用体验与 Lombok一致（仅声明依赖）。

## 技术方案
- 使用标准注解处理器（JSR-269）作为入口，在编译期通过 `Trees` 获取 `JCClassDecl`，用 `TreeMaker` 将 `public static final class Meta` 注入到目标类体内，并注入两个常量字段。
- 为避免微服务侧 `--add-exports`，在组件 jar 内置一个 Java Agent，通过 `Instrumentation.redefineModule` 在编译器进程启动时打开 `jdk.compiler/com.sun.tools.javac.{api,processing,tree,util,code}` 包；处理器初始化不再触发 `IllegalAccessError`。
- 根工程集中一次性为所有子项目的 `JavaCompile` 追加 `-javaagent:<组件jar绝对路径>`，微服务模块无需任何配置（与 Lombok体验等价：只声明依赖即可）。

## 实施步骤
1) 完善处理器逻辑（组件内）：
- 删除注解节点（仅编译期使用），避免源保留；
- 注入前做去重检查，避免二次生成；
- 为生成的内部类与字段设置“已生成”标记（轻量实现，避免影响 IDE/增量编译）；
- 校验目标类：仅允许顶层类或静态嵌套类；对非法用法报编译期错误消息；
- 二进制名获取使用 `ElementUtils.getBinaryName`，保证嵌套 `$` 表达一致。

2) Agent 与集中配置：
- 组件 jar Manifest 声明 `Premain-Class` 与 `Agent-Class`；
- 根工程在 `subprojects { tasks.withType(JavaCompile) { options.fork = true; options.forkOptions.jvmArgs += "-javaagent:<组件jar绝对路径>" } }` 统一追加一次；
- 移除各模块中临时的 `-Xplugin` 与 `--add-exports` 配置，保持仓库整洁。

3) 验证与清理：
- 在 `component/test` 编译验证：`NormalClass.Meta.SIMPLE_CLASS_NAME/CLASS_NAME`、`Outer.Inner.Meta.CLASS_NAME`；
- 确认所有模块无需手工参数即可编译通过；
- 保留注解默认内部类名为 `Meta`；替换旧引用为 `Class.Meta.*`。

## 交付
- 组件内：处理器与 Agent 代码、服务清单、统一根工程配置；
- 验证报告：构建日志与关键类的断言；
- 不改动 `temp/lombok` 的任何文件。
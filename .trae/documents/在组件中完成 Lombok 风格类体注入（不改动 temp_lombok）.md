## 约束与目标
- 不改动 `temp/lombok`（仅参考）。
- 在自定义组件中，编译期在“当前类体”注入 `public static final class Meta`，仅生成 `SIMPLE_CLASS_NAME`、`CLASS_NAME`。
- 微服务无需逐模块编译参数；统一集中到根工程，整体体验等价于 Lombok（只需依赖）。

## 技术方案
- 使用 JSR‑269 注解处理器在编译期获取 `JCClassDecl`，用 `TreeMaker` 注入内部类与常量（对齐 Lombok的 AST 注入逻辑）。
- 组件内置 Java Agent，通过 `Instrumentation.redefineModule` 打开 `jdk.compiler` 的内部包，避免 `--add-exports`；处理器初始化即可访问 `com.sun.tools.javac.*`。
- 在根工程统一为所有 `JavaCompile` 追加一次 `-javaagent:<组件jar>`；各微服务不需要再写任何编译参数。

## 处理器完善
- 删除注解节点（仅编译期使用）。
- 注入前做去重；为生成成员设置轻量“已生成”标记。
- 校验目标类：仅允许顶层/静态嵌套类；非法用法抛编译期错误。
- 二进制名使用 `ElementUtils.getBinaryName((TypeElement)e)`，嵌套名按 `$` 生成。

## 集中配置与清理
- 根 `build.gradle` 在 `subprojects` 中为所有 `JavaCompile` 统一追加 `-javaagent:<组件jar绝对路径>`。
- 移除各模块临时的 `-Xplugin`、`--add-exports` 配置。

## 验证
- 在 `component/test` 编译验证：`NormalClass.Meta.SIMPLE_CLASS_NAME/CLASS_NAME`、`Outer.Inner.Meta.CLASS_NAME`。
- 全工程编译通过，无需微服务侧额外参数。

## 交付
- 完成处理器与 Agent 的组件内实现、统一根工程 JVM 参数、移除临时编译参数；提供编译与示例验证结果。
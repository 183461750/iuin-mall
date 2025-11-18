## 约束
- 不改动 `temp/lombok`（仅作参考）。
- 目标：在自定义组件内按 Lombok 逻辑实现“类体内注入”，默认内部类 `Meta`，仅生成 `SIMPLE_CLASS_NAME` 与 `CLASS_NAME`，微服务无需逐模块编译参数。

## 实施项
1) 处理器对齐
- 删除注解节点（仅编译期使用）：在处理器 `process` 中移除 `@ClassSignatureConstants` 的 AST 注解。
- 去重与生成标记：注入前检查内部类是否存在，设置轻量“已生成”标记，避免重复注入。
- 用法校验：仅允许顶层类或静态嵌套类；不满足时报编译期错误。
- 二进制名：使用 `ElementUtils.getBinaryName((TypeElement)e)`，保证嵌套 `$`。

2) 模块开放（统一一次）
- 保留组件 jar 内置 Agent（已实现）；在根工程 `subprojects` 的 `JavaCompile` 统一追加一次 `-javaagent:<组件jar绝对路径>`，确保编译器进程启动即打开 `jdk.compiler` 内部包。
- 移除各模块的临时 `-Xplugin/--add-exports`，实现零手工参数。

3) 验证与清理
- 在 `component/test` 编译验证：`NormalClass.Meta.SIMPLE_CLASS_NAME/CLASS_NAME`、`Outer.Inner.Meta.CLASS_NAME`。
- 替换旧引用为 `Class.Meta.*`；保留默认内部类名 `Meta`。

## 交付
- 组件内处理器与 Agent 完成；根工程集中 `-javaagent` 配置；移除临时编译参数；提供编译通过记录与示例类验证。
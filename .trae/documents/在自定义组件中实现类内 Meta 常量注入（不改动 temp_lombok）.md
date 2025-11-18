## 目标
- 不改动 `temp/lombok` 任何代码（仅用作参考）。
- 在自定义组件中按 Lombok 的逻辑实现“编译期在类体注入内部类”，默认内部类名 `Meta`，仅生成 `SIMPLE_CLASS_NAME` 与 `CLASS_NAME`。
- 微服务模块不需要逐模块配置编译参数；统一在根工程集中一次性配置，使整体使用体验与 Lombok一致（仅声明依赖）。

## 技术方案
- 注解处理器（JSR‑269）：在 `compile-time-code-gen` 模块实现处理器，使用 `Trees` + `TreeMaker` 对被注解类的 `JCClassDecl` 进行 AST 注入，加入 `public static final class Meta`、私有构造和两个常量字段。
- 打开 JDK17 编译器内部包：组件 jar 内置 Java Agent，通过 `Instrumentation.redefineModule` 在编译器进程启动时统一开放 `com.sun.tools.javac.{api,processing,tree,util,code}` 包，处理器无需 `--add-exports`。
- 根工程集中一次性追加 `-javaagent:<组件jar绝对路径>` 到所有 `JavaCompile` 任务；微服务模块无需任何编译参数，保持与 Lombok一致的使用体验。

## 详细实施
1) 处理器完善
- 删除注解节点（仅编译期使用）。
- 注入前做去重检查，避免重复生成；为生成成员设置“已生成”标记。
- 校验目标类：仅允许顶层类或静态嵌套类；非法用法抛编译期错误。
- 二进制名使用 `ElementUtils.getBinaryName`，嵌套类以 `$` 表达。

2) Agent与集中配置
- 组件 jar Manifest 声明 `Premain-Class` 与 `Agent-Class`。
- 根工程在 `subprojects { tasks.withType(JavaCompile) { options.fork = true; options.forkOptions.jvmArgs += "-javaagent:<组件jar绝对路径>" } }` 统一追加一次。
- 移除各模块的临时 `-Xplugin` 与 `--add-exports` 配置，保留统一的根工程 JVM 参数。

3) 验证与清理
- 在 `component/test` 编译验证：`NormalClass.Meta.SIMPLE_CLASS_NAME/CLASS_NAME`、`Outer.Inner.Meta.CLASS_NAME`。
- 全工程编译通过，无需微服务侧额外配置；保留默认内部类名为 `Meta`，统一将旧引用替换为 `Class.Meta.*`。

## 保证
- 全程不改动 `temp/lombok` 的任何文件；仅参考其 Handler 逻辑与 SPI用法。
- 交付后各微服务只需声明依赖即可获得 `Class.Meta.*` 常量，体验与 Lombok一致。
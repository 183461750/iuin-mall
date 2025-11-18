## 目标
- 在不改动 Lombok 源码的前提下，按 Lombok 的实现思路在“自定义组件”中完成：编译期向被注解类体内注入 `public static final class Meta`，提供 `SIMPLE_CLASS_NAME` 与 `CLASS_NAME`。
- 微服务模块无需配置 `-Xplugin` 或 `--add-exports`，只需保留现有 Lombok 依赖与本组件的 `annotationProcessor` 依赖即可使用。

## 技术路线（对齐 Lombok思路，但在自组件内实现）
1) 注解处理器（JSR-269）+ Javac AST 注入
- 在 `compile-time-code-gen` 模块新增处理器 `InnerMetaInjectorProcessor`：
  - 扫描 `@ClassSignatureConstants` 的类；通过 `com.sun.source.util.Trees` 定位到 `JCClassDecl`；
  - 使用 `com.sun.tools.javac.tree.TreeMaker` 注入 `public static final class Meta`、私有构造、两个常量字段。
- 注入逻辑参考 Lombok `HandleFieldNameConstants` 的类型/成员注入流程，但实现代码完全在自定义组件中。

2) 自动开放 JDK17 模块（避免微服务侧 add-exports）
- 在组件新增“自附加 Java Agent”：
  - `MetaAgent(Premain-Class)`：在 `premain(Instrumentation)` 通过反射调用 `jdk.internal.module.Modules`，对当前未命名模块追加 `addExports`：`jdk.compiler/com.sun.tools.javac.{api,processing,tree,util,code}`。
  - `ModuleOpener.ensureOpen()`：处理器启动时调用；如果未开放，则尝试自附加当前 JVM 进程：
    - 通过 `com.sun.tools.attach.VirtualMachine.attach(pid)` 自附加，并 `loadAgent(agentJar)`；
    - `agentJar` 路径从处理器自身 `CodeSource` 解析或打包到同模块。
- 完成后，处理器可直接引用 `com.sun.tools.javac.*`，无需外部 `--add-exports`。

3) 组件打包与依赖
- 组件生成一个 jar：同时包含 `Processor`（`META-INF/services/javax.annotation.processing.Processor`）与 `Agent`（`Premain-Class`），无需拆分。
- 根工程统一声明：
  - `compileOnly 'org.projectlombok:lombok'`
  - `annotationProcessor project(':component:pluggable-annotation:compile-time-code-gen')`
- 不要求微服务添加任何编译参数或插件。

4) 行为与配置
- 默认内部类名改为 `Meta`（已完成）；支持注解参数 `innerClassName` 覆盖。
- 仅生成两项类名常量；保持精简，不生成方法签名（可后续扩展）。

5) 验证
- 在 `component/test` 中验证：
  - `NormalClass.Meta.SIMPLE_CLASS_NAME/CLASS_NAME`
  - 嵌套类 `Outer.Inner.Meta.CLASS_NAME` 二进制名包含 `$`。
- Gradle 编译：`./gradlew :component:test:clean :component:test:compileJava -x test`；无 `-Xplugin/--add-exports`。

6) 清理
- 删除现有 `MetaConstantsPlugin`（Javac 插件）与各模块的 `--add-exports/-Xplugin` 临时配置，切换到“处理器 + 自附加 Agent”的正式实现。

7) 风险与兼容
- 自附加 Agent需 `jdk.attach` 模块存在；JDK17 默认可用；在极端受限环境可能需开放 `jdk.attach`。
- 处理器在 IDE/Gradle 的注解处理阶段运行；IDE需启用注解处理（与 Lombok一致）。

## 交付
- 新增处理器与 Agent 源码、打包配置与服务清单文件；
- 更新根工程统一依赖；
- 完成端到端编译验证与测试示例；
- 清理临时 Javac 插件路径，最终微服务无额外编译参数。
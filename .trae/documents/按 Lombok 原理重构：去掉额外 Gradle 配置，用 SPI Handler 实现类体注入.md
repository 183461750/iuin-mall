## 研究结论（Lombok 精髓）
- **加载机制**：通过 SPI (`META-INF/services/lombok.javac.JavacAnnotationHandler`) 被 `HandlerLibrary` 自动发现并调度；每个 Handler 继承 `JavacAnnotationHandler<T>`，在编译期直接修改 AST（`JCTreeMaker` 注入内部类型与成员）。
- **无需 Gradle add-exports**：`LombokProcessor.addOpensForLombok()` 运行时反射 + `Unsafe` 打开 `jdk.compiler` 的 `com.sun.tools.javac.*` 包，避免用户传入 `--add-opens/--add-exports`；并禁用 JDK9 的非法访问日志（`AnnotationProcessor.disableJava9SillyWarning`）。
- **注入模式**：参考 `HandleFieldNameConstants`，在目标类体内创建 `public static final class Fields/enum`，成员存在性检查、生成私有构造、标注 generated-by。

## 重构目标
- 去掉已加的所有“乱七八糟的 Gradle 额外配置”（全局与模块级的 `--add-exports/-Xplugin/-javaagent`）。
- 实现一个 **Lombok SPI Handler**，专门处理 `@ClassSignatureConstants`，在“当前类体”注入默认内部类 `Meta`，并生成 `SIMPLE_CLASS_NAME` 与 `CLASS_NAME` 两常量。
- 不改动 `temp/lombok` 源码，仅参考其实现；将 Handler 写在你的组件里并打包为 **独立扩展 jar**，由 Lombok 在编译时加载。

## 实施步骤
1) **新增 Handler（组件内）**
- 新类：`com.iuin.component.pluggable_annotation.compile_time_code_gen.lombok.HandleClassSignatureConstants`
- 继承：`lombok.javac.JavacAnnotationHandler<ClassSignatureConstants>`
- 逻辑：
  - 删除注解节点（仅编译期使用）
  - 校验：目标类为顶层或静态嵌套类
  - 注入 `public static final class Meta`（或注解参数覆盖），生成私有构造
  - 注入 `public static final String SIMPLE_CLASS_NAME/CLASS_NAME`；类名用 `ElementUtils.getBinaryName`（含 `$`）
  - 做成员存在性检查与 `generated-by` 标记

2) **SPI 注册（组件内）**
- 添加 `META-INF/services/lombok.javac.JavacAnnotationHandler`，内容为 Handler 的 FQCN

3) **依赖与打包（组件内）**
- 依赖：`compileOnly 'org.projectlombok:lombok'`（提供 `lombok.javac.*` 与 `lombok.core.*` 编译期 API）
- 打包为扩展 jar；发布到 `mavenLocal`（或本地仓库坐标）

4) **工程接入（根工程统一）**
- 在 `subprojects` 统一声明：
  - `compileOnly 'org.projectlombok:lombok'`
  - `annotationProcessor 'org.projectlombok:lombok'`
  - `annotationProcessor 'com.iuin.component.pluggable-annotation:lombok-meta-extension:<version>'`（即本扩展 jar）
- **不添加**任何 `--add-exports`、`-Xplugin` 或 `-javaagent` 配置

5) **清理**
- 删除之前为“JSR-269 + Agent + Javac 插件”路径添加的所有 Gradle 额外配置
- 保留默认内部类名为 `Meta`，统一把旧引用改为 `Class.Meta.*`

6) **验证**
- 编译 `component/test`：
  - `NormalClass.Meta.SIMPLE_CLASS_NAME/CLASS_NAME`
  - `Outer.Inner.Meta.CLASS_NAME`（包含 `$`）
- IDE 仅需开启注解处理（与 Lombok一致），无需任何额外 JVM 参数

## 交付
- 完成 Handler 与 SPI 的组件内实现与发布；
- 根工程统一接入扩展依赖；
- 清理所有“额外 Gradle 参数”；
- 提供编译通过与示例验证日志。
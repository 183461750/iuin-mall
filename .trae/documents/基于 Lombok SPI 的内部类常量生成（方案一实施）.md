## 目标
- 与 Lombok 一致的编译期注入：不需要 `-Xplugin` 或 `--add-exports`，在“当前类体”内生成内部类（默认 `Meta`），仅包含 `SIMPLE_CLASS_NAME` 与 `CLASS_NAME`。
- 全局可用：微服务模块无需额外编译参数；保留现有 `@ClassSignatureConstants` 注解的语义（可扩展 `innerClassName/prefix/suffix`）。

## 关键思路
- 复用 Lombok 的 Handler/SPI 机制：实现 `JavacAnnotationHandler<ClassSignatureConstants>`，由 Lombok 的 `HandlerLibrary` 通过 SPI 自动加载。
- 注入策略：直接在编译期修改目标类 AST，添加内部类型 `Meta` 与常量字段，风格对齐 `@FieldNameConstants`。

## 实施步骤
1) 在 Lombok 源码树中新增 Handler
- 目录：`temp/lombok/src/core/lombok/javac/handlers/HandleClassSignatureConstants.java`
- 继承：`lombok.javac.JavacAnnotationHandler<ClassSignatureConstants>`
- 逻辑：
  - 删除注解节点（只编译期用）
  - 在类体中插入 `public static final class {innerName}`（默认 `Meta`），且注入私有构造
  - 添加 `public static final String SIMPLE_CLASS_NAME/CLASS_NAME` 两字段（按二进制名规则，嵌套用 `$`）
  - 仅保留最小生成（不做方法签名），支持注解参数覆盖 `innerClassName`
- 工具：使用 `JavacTreeMaker` 与 `JavacHandlerUtil`，参照 `HandleFieldNameConstants` 与现有 `ClassConstantHandler` 的注入模式

2) SPI 注册
- 方式A：使用 Lombok 的 `@lombok.spi.Provides`，由 `spiProcessor` 自动生成服务映射
- 方式B：手工添加 `META-INF/services/lombok.javac.JavacAnnotationHandler`，内容包含新 Handler 的 FQCN

3) 构建与发布自定义 Lombok 扩展
- 使用 `temp/lombok/build.xml`（Ant）生成包含新 Handler 的 Lombok 扩展 jar，或将其作为独立扩展 jar（推荐独立坐标，例如 `com.iuin.lombok:lombok-meta-extension`）
- 发布到 `mavenLocal` 以供根工程解析

4) 根工程接入
- 在 `build.gradle` 的 `subprojects` 里统一声明：
  - `compileOnly 'org.projectlombok:lombok'`
  - `annotationProcessor 'org.projectlombok:lombok'`
  - `annotationProcessor 'com.iuin.lombok:lombok-meta-extension:VERSION'`（新增扩展）
- 消费者不需要任何 `--add-exports` 或 `-Xplugin` 配置；IDE 只需开启注解处理即可

5) 清理与迁移
- 删除当前 Javac 插件 `MetaConstantsPlugin` 及其 `-Xplugin` 的编译参数
- 保持 `@ClassSignatureConstants(innerClassName="Meta")` 默认值；替换现有代码里对 `<Class>_Signatures` 或 `Signatures` 的访问为 `Class.Meta.*`

6) 验证
- 在 `component/test` 模块使用注解，编译时自动生成 `Class.Meta.SIMPLE_CLASS_NAME/CLASS_NAME`
- 验证嵌套类、泛型类、内部静态类等场景的二进制名规则（`Outer$Inner`）
- Gradle 构建：
  - 先构建并发布扩展 jar 到本地仓库
  - 再编译 `component:test`，不配置任何 `-Xplugin/--add-exports`

## 交付与影响
- 微服务模块零编译参数，体验与 Lombok 一致
- 小幅新增一个本地扩展依赖（annotationProcessor），通过根工程统一声明即可
- 现有 `MetaConstantsPlugin` 代码与相关配置将被移除，避免后续维护成本
## 目标
- 默认内部类名改为 `Meta`，只注入 `SIMPLE_CLASS_NAME` 与 `CLASS_NAME` 两个常量。
- 完全移除顶层 `<Class>_Signatures` 相关实现与依赖，代码保持最精简。
- 测试类统一放到 `component/test/src/main/java/com/iuin/component/test/compile_time_code_gen`，通过 Gradle 编译验证。

## 现状依据
- 注解默认内部类名位置：`component/pluggable-annotation/compile-time-code-gen/src/main/java/com/iuin/component/pluggable_annotation/compile_time_code_gen/annotation/ClassSignatureConstants.java:51`（当前为 "Signatures"）。
- 顶层类生成器位置：`component/pluggable-annotation/compile-time-code-gen/src/main/java/com/iuin/component/pluggable_annotation/compile_time_code_gen/processor/ClassSignatureConstantsProcessor.java:57-63`（生成 `<Class>_Signatures`）。
- Lombok AST 注入参考：`compile-time-code-gen/src/main/java/com/iuin/annotation/handler/ClassConstantHandler.java:51-127`（在类体内注入内部类型与字段）。
- 测试模块已引入编译期组件：`component/test/build.gradle:26-27`。

## 调整项
### 1) 修改注解默认值
- 将 `innerClassName()` 默认改为 `"Meta"`：`ClassSignatureConstants.java:51`。

### 2) 新增精简版 Lombok Handler
- 新增 `com.iuin.component.pluggable_annotation.compile_time_code_gen.handler.HandleClassSignatureConstants`，继承 `lombok.javac.JavacAnnotationHandler<ClassSignatureConstants>`。
- 行为：删除注解节点；在目标类体内注入 `public static final class Meta`（或使用注解参数）；注入私有构造；注入两个字段：
  - `SIMPLE_CLASS_NAME`：去包后的二进制名。
  - `CLASS_NAME`：完整二进制名（含嵌套 `$`）。
- 代码风格与注入方式参考 `ClassConstantHandler.java:51-127`。

### 3) 注册 SPI
- 在 `compile-time-code-gen/src/main/resources/META-INF/services/lombok.javac.JavacAnnotationHandler` 追加：
  - `com.iuin.component.pluggable_annotation.compile_time_code_gen.handler.HandleClassSignatureConstants`

### 4) 彻底移除顶层类生成
- 删除文件：`component/pluggable-annotation/compile-time-code-gen/src/main/java/com/iuin/component/pluggable_annotation/compile_time_code_gen/processor/ClassSignatureConstantsProcessor.java:1-116`。
- 移除仅用于 Processor 的依赖：`component/pluggable-annotation/compile-time-code-gen/build.gradle:36-41`（auto-service）与 `:40`（javapoet）；若模块仅保留 Lombok Handler，这些可删除。
- 删除/调整任何引用 `<Class>_Signatures` 的测试或样例文件（例如 `component/pluggable-annotation/.../TestClassSignatureConstants.java:19-28` 改为内部类 `Meta` 访问，或迁移到组件测试包）。

### 5) 测试类与用法
- 位置：`/component/test/src/main/java/com/iuin/component/test/compile_time_code_gen`（目录已存在，含 `NormalClass.java` 等）。
- 用法示例：
  - `@ClassSignatureConstants` → `MyType.Meta.SIMPLE_CLASS_NAME`、`MyType.Meta.CLASS_NAME`。
  - 如需手动示例，删除旧的 `UserService_Signatures.java`，或改为 `UserService.Meta` 访问。

## Gradle 验证
- 构建 Handler 组件：`./gradlew :component:pluggable-annotation:compile-time-code-gen:build`
- 构建测试模块：`./gradlew :component:test:clean :component:test:build`
- 期望：示例类成功编译，且能访问 `Class.Meta.*` 常量。

## 兼容性
- JDK17 的 `--add-exports` 已在组件编译任务配置：`component/pluggable-annotation/compile-time-code-gen/build.gradle:48-56`。
- IDE 需启用注解处理；仓库已普遍使用 Lombok，无需额外插件。

## 迁移提示
- 将所有 `Class.Signatures.*` 或 `<Class>_Signatures.*` 的引用替换为 `Class.Meta.*`；不再引入 Processor 相关工件或依赖。
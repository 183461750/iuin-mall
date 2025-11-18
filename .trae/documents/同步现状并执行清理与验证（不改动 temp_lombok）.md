## 现状摘要
- 组件已具备 Lombok SPI Handler：
  - `component/pluggable-annotation/compile-time-code-gen/src/main/java/.../lombok/HandleClassSignatureConstants.java`（类体注入 Meta、两常量、删除注解、静态类校验）。
  - `META-INF/services/lombok.javac.JavacAnnotationHandler` 已注册。
- 依赖：`compile-time-code-gen/build.gradle:31-37` 使用 `compileOnly/annotationProcessor 'org.projectlombok:lombok:1.18.24'`。
- 仍残留不必要的 Gradle 设施：
  - `java-gradle-plugin` 插件与 `gradlePlugin {}` 块（`compile-time-code-gen/build.gradle:7, 74-81`）。
  - 编译器导出参数（`compile-time-code-gen/build.gradle:41-54`）。
- 工程级配置（根与 settings）已基本还原，无额外 JVM 参数；`component/test/build.gradle` 依赖组件为 `annotationProcessor`，符合扩展 jar接入模式。

## 清理与执行计划
1) 去除无关 Gradle 插件与导出参数：
- 从 `compile-time-code-gen/build.gradle` 删除 `id 'java-gradle-plugin'` 与 `gradlePlugin { ... }`。
- 删除 `tasks.withType(JavaCompile)...` 中的所有 `--add-exports` 与 `compilerArgs`。
2) 仅保留必要依赖与发布：
- 保持 `compileOnly/annotationProcessor 'org.projectlombok:lombok:1.18.24'`。
- 发布到 `mavenLocal`（或直接通过 project 依赖传递），维持最简配置。
3) 验证：
- 构建组件：`./gradlew :component:pluggable-annotation:compile-time-code-gen:build -x test`。
- 构建测试：`./gradlew :component:test:clean :component:test:compileJava -x test`。
- 断言 `NormalClass.Meta.SIMPLE_CLASS_NAME/CLASS_NAME` 与 `Outer.Inner.Meta.CLASS_NAME`（含 `$`）。

## 说明
- 全程不改动 `temp/lombok` 目录。
- 使用体验与 Lombok一致：仅声明 `annotationProcessor` 扩展依赖即可，零额外 JVM 参数。

## 交付
- 清理过的 `build.gradle`（组件内）、可用的 SPI 扩展、编译通过日志与示例验证。
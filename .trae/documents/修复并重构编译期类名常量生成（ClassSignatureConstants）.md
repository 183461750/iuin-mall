## 现状与根因
- 参考：Lombok 的处理器将“内部类型”注入到被注解类中（temp/lombok/src/core/lombok/javac/handlers/HandleFieldNameConstants.java:59-80、133-191），并完成类型与成员校验。
- 当前实现仅用标准 Processor 生成包级顶层 `Clazz`，未能在目标类内生成 `innerClassName`，与期望的 `TargetClass.Signatures` 使用方式不符（component/.../ClassSignatureConstantsProcessor.java:47-76）。
- 注解参数基本未使用：`asEnum/prefix/suffix/onlyPublicMethods/includeInheritedMethods/innerClassName` 未被处理（ClassSignatureConstantsProcessor.java:47-81）。
- 常量内容不满足“类名（不含包名）”要求；且未处理嵌套类的二进制名（`$`）（ClassSignatureConstantsProcessor.java:48-63）。
- 服务声明错误：`META-INF/services/javax.annotation.processing.Processor` 指向了错误 FQN（src/main/resources/META-INF/services/javax.annotation.processing.Processor:1），导致处理器发现失败。
- 项目已基于 JDK17（build.gradle:14-20、gradle.properties:1-3），无需降级。

## 目标与原则
- 在 JDK17 下，生成“类名（不含包名）”常量，正确处理静态嵌套类与泛型类；匿名类不支持注解（不生成）。
- 内部类型名称默认 `Signatures`，支持 `asEnum/prefix/suffix/innerClassName`；性能开销极小；错误处理友好。

## 技术方案
- 重构为 Lombok Javac 处理器（优先）：新增 `HandleClassSignatureConstants`，仿照 Lombok 的注入模式：
  - 校验：仅 class/enum/record；禁止非静态嵌套类（复用 `isClassEnumOrRecord/isStaticAllowed`）。
  - 生成内部类型：`static final class` 或 `enum`（由 `asEnum` 决定），名称取 `innerClassName`。
  - 注入成员：
    - `CLASS_NAME`（二进制限定名，含包与 `$`）：`((JCClassDecl) typeNode.get()).sym.flatname.toString()`。
    - `SIMPLE_CLASS_NAME`（不含包名的二进制简单名，对嵌套类为 `Outer$Inner`）：从 `flatname` 去掉包前缀；或基于 `getQualifiedName` 去掉包、将 `.` → `$`。
    - 应用 `prefix/suffix` 到常量名（例如 `PREFIX_SIMPLE_CLASS_NAME_SUFFIX`）。
  - 冲突处理：若已有同名内部类型或字段，跳过并发出 warning（与 Lombok 风格一致）。
  - SPI：使用 `lombok.spi.Provides` 自动注册；移除当前无效 `lombok.javac.HandlerLibrary` 声明。
- 保留标准 Processor（兜底）：修正服务声明 FQN，生成包内顶层 `Signatures` 类，包含同名常量（用于无 Lombok SPI 的编译环境）。

## 详细实现步骤
1) 新增 `javac/handlers/HandleClassSignatureConstants.java`：
- 入口 `handle(...)` 解析注解参数；校验 `innerClassName` 合法性；读取并应用配置。
- `generateForType(...)`：创建/查找内部类型；按 `asEnum` 选择类/枚举；构造常量字段或枚举值，设置初始化字符串。
- 嵌套与泛型处理：使用 `flatname` 保证 `$`；简单名不包含类型参数。
2) 修复标准 Processor：
- 修正 `META-INF/services/javax.annotation.processing.Processor` 为 `com.iuin.component.pluggable_annotation.compile_time_code_gen.processor.ClassSignatureConstantsProcessor`。
- Processor 中读取注解参数，生成包内顶层 `innerClassName` 类型，字段同上（SIMPLE/CLASS）。
3) 依赖与 JDK17：
- 维持 Gradle JDK17 toolchain；Lombok 1.18.24 与 JavaPoet 1.13.0 均兼容。
4) 测试验证（component/test/src/main/java/...）：
- 普通类 `NormalClass`：断言 `Signatures.SIMPLE_CLASS_NAME == "NormalClass"`。
- 内部类 `Outer.Inner`（静态）：断言 `Signatures.SIMPLE_CLASS_NAME == "Outer$Inner"`。
- 匿名类：在方法中创建匿名类，确认不会生成常量（访问常量应不存在）。
- 泛型类 `GenericClass<T>`：断言 `Signatures.SIMPLE_CLASS_NAME == "GenericClass"`。
- 如需在单测中引用，被测类放在 `component/test`；在 `compile-time-code-gen` 的 `testImplementation` 中添加对 `component:test` 的项目依赖（实施时调整 Gradle）。

## 错误处理与性能
- 错误：类型不支持/非静态嵌套/非法标识符 → error；已存在冲突成员 → warning。
- 性能：仅字符串计算与常量注入，无需方法遍历；编译时影响可忽略。

## README 补充（JDK17 项目）
- 新增“环境与兼容性”章节，明确 JDK17 要求与 Lombok 版本；
- 新增“类名常量”章节，说明 `CLASS_NAME/SIMPLE_CLASS_NAME` 的定义与嵌套类表现（`$`）；
- 新增“两种处理器模式”说明：Lombok SPI（内部类注入）与标准 Processor（顶层类）差异与选择；
- 新增示例覆盖普通/内部/泛型/匿名场景；
- 新增常见错误与解决（服务声明、开启注解处理、Gradle 配置）。

## 交付
- 提交处理器实现与服务声明修复；
- 提交测试用例（覆盖四类场景）；
- 更新 README，加入 JDK17 说明与使用示例；
- 提供变更清单与关键代码位置说明，便于审阅。
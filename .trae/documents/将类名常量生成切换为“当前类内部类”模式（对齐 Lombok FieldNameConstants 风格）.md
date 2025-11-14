## 方案对比
### 1) 编译器插件模式独立实现
- 架构：基于 Javac/Eclipse 编译器 API，在编译期直接修改 AST，把 `Signatures` 内部类与常量注入目标类；或注册 `com.sun.source.util.Plugin` + `TaskListener`。
- 核心能力：可在“现有类体”内新增类型与成员（满足内部类需求）；标准 JSR 269 注解处理器仅能“生成新源文件”，不能注入现有类，需走编译器插件。
- IDE/JDK 兼容：JDK17 需开启 `--add-exports=jdk.compiler/com.sun.tools.javac.{api,processing,tree,util,code}=ALL-UNNAMED`；IDE 侧需启用注解处理或插件支持。
- 优势：完全自主可控，不依赖第三方；可制定稳定 API。
- 挑战：开发与维护成本高；需深度适配不同 JDK/IDE；编译器内部 API 变动可能导致升级维护压力。
- 性能：注入常量为 O(1) 操作，编译期微小开销。

### 2) 基于 Lombok 的集成
- 架构：复用 Lombok 的 Handler/SPI 机制，编写 `JavacAnnotationHandler<ClassSignatureConstants>`，通过 SPI 被 Lombok 的 `HandlerLibrary` 加载，直接在 AST 注入内部类与常量。
- 参考点：
  - 处理入口与注入：temp/lombok/src/core/lombok/javac/handlers/HandleFieldNameConstants.java:59-80, 133-191。
  - Handler 加载：temp/lombok/src/core/lombok/javac/HandlerLibrary.java:162-191。
  - SPI 注解：temp/lombok/src/spiProcessor/lombok/spi/Provides.java:29-33。
- IDE/JDK 兼容：IDE 原生支持 Lombok；JDK17 同样需 `--add-exports`；版本升级需跟随 Lombok 发布节奏。
- 优势：开发效率高、生态成熟、IDE 兼容性好；快速实现“始终生成内部类”。
- 挑战：对 Lombok 内部 API 有依赖，存在版本兼容风险；功能边界受 Lombok 设计影响（但本需求可满足）。
- 性能：与方案1类似，注入常量开销极小；Lombok已有优化。

## 可行性评估
- 功能完整性：
  - 独立插件：100% 满足内部类注入（走编译器 API），JSR269单独无法满足。
  - Lombok 集成：100% 满足（直接复用其 AST 注入能力）。
- 维护成本：
  - 独立插件：高（适配多 JDK/IDE 与内部 API 变化）。
  - Lombok 集成：中（跟随 Lombok 版本升级，变更面较小）。
- 兼容性：
  - 独立插件：需严密处理 `--add-exports` 与 IDE 插件，跨 IDE/JDK 升级需要回归。
  - Lombok 集成：IDE 与构建系统普遍已有支持；仍需 `--add-exports`。
- 性能影响：两者均为编译期微量；基本可忽略。
- 团队技术储备：
  - 独立插件：需深入掌握 Javac/Eclipse 内部；上手成本高。
  - Lombok 集成：对齐团队现有使用经验，开发/排障更快。

## 建议
- 推荐采用“基于 Lombok 的集成方案”为主，实现目标类内部类的稳定生成；同时保留标准 Processor 顶层类的兜底以便在 Lombok SPI 不可用的环境下不阻断编译。
- 理由：功能完全满足；研发效率高；IDE 兼容；维护复杂度低于自研编译器插件。

## 下一步计划
1. 逆向分析 Lombok 关键模块：
- 处理器：`HandleFieldNameConstants`（注入内部类型/字段流程）
- 加载：`HandlerLibrary.load` 与 `SpiLoadUtil.findServices`
- SPI：`@lombok.spi.Provides` 与生成的服务映射
2. POC 实现：
- POC-A（Lombok 集成）：编写 `JavacAnnotationHandler<ClassSignatureConstants>`，在 AST 注入 `innerClassName`（默认 `Signatures`）与 `SIMPLE_CLASS_NAME/CLASS_NAME`；添加 SPI。
- POC-B（独立插件）：实现 `com.sun.source.util.Plugin` 监听编译任务，在类定义节点上插入内部类型与常量；准备 `META-INF/services/com.sun.source.util.Plugin`。
3. 基准与兼容测试：
- JDK17/21 编译耗时对比；IDE（IntelliJ/Eclipse）编译与提示；跨模块/多注解协作；嵌套类与泛型类场景验证。
4. 产出与决策：
- 汇总测试数据与维护评估，确认“Lombok 集成为主 + 标准 Processor 兜底”的最终实施方案；更新 README 与使用指南（JDK17 导出参数与示例）。
# Compile-time Code Gen 组件（JDK 17）

一个编译时期自动生成类名和方法签名常量的注解处理器组件，类似于 Lombok 的 `@FieldNameConstants`，但专注于生成类名常量，并适配 JDK 17。

## 环境与兼容性
- 要求 JDK 17；Gradle 已配置 `toolchain` 指向 Java 17。
- 依赖 `org.projectlombok:lombok:1.18.24` 作为编译期处理器；支持 Lombok SPI 与标准 Processor 两种模式：
  - 标准 Processor 模式（当前默认）：在包内为每个被注解类型生成顶层 `<TypeSimpleName>_Metas` 类，避免命名冲突。
  - Lombok SPI 模式（可选扩展）：在被注解类内部生成 `Metas`（或自定义名）类型；如启用需在 JDK17 环境下增加编译器导出参数并引入 Lombok SPI 处理器。

## 功能特性

- 在编译时期自动生成类名和方法签名常量
- 支持两种常量生成模式：静态常量类（默认）和枚举
- 支持自定义常量名前缀和后缀
- 可配置只包含公共方法
- 可配置是否包含继承的方法
- 支持自定义内部常量类名
- 类型安全的常量引用
- 减少魔法字符串，提高代码可维护性

## 快速开始

### 添加依赖

在项目的build.gradle中添加：

```groovy
implementation project(':component:pluggable-annotation:compile-time-code-gen')
annotationProcessor project(':component:pluggable-annotation:compile-time-code-gen')
```

### 基本使用（类名常量）

```java
import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassMetaConstants;

@ClassMetaConstants
public class UserService {
    public void createUser(String username, String password) {
        // 方法实现
    }
    
    public User findUserById(long id) {
        return null;
    }
}

// 使用生成的类名常量
String simple = UserService_Metas.SIMPLE_CLASS_NAME; // 不含包名："UserService"
String full = UserService_Metas.CLASS_NAME; // 二进制全名："com.xxx.UserService"
```

## 注解参数说明

@ClassMetaConstants 注解支持以下参数：

| 参数名 | 类型 | 默认值 | 说明 |
|-------|------|-------|------|
| asEnum | boolean | false | 是否使用枚举模式生成常量 |
| prefix | String | "" | 常量名前缀 |
| suffix | String | "" | 常量名后缀 |
| onlyPublicMethods | boolean | false | 是否只包含公共方法 |
| includeInheritedMethods | boolean | false | 是否包含继承的方法 |
| innerClassName | String | "Metas" | 生成的内部常量类名 |

## 使用示例

### 1. 使用枚举模式

```java
@ClassMetaConstants(asEnum = true)
public class ProductService {
    public List<Product> findAll() {
        return new ArrayList<>();
    }
}

// 使用枚举常量名（如需枚举值，请自行扩展枚举实现）
String simpleConstName = ProductService_Metas.SIMPLE_CLASS_NAME; // 顶层签名类常量名
```

### 2. 自定义前缀和后缀

```java
@ClassMetaConstants(prefix = "META_", suffix = "_SIG")
public class OrderService {
    public void processOrder(long orderId) {
        // 方法实现
    }
}

// 使用自定义前缀后缀的常量名（示例）
String simpleConst = OrderService_Metas.META_SIMPLE_CLASS_NAME_SIG; // "UserService"
```

### 3. 只包含公共方法

```java
@ClassMetaConstants(onlyPublicMethods = true)
public class SecurityUtil {
    public static boolean isAuthenticated() {
        return false;
    }
    
    private static String getSessionId() {
        return "";
    } // 私有方法不会生成常量（如启用方法签名生成时）
}
```

### 4. 包含继承的方法

```java
public class BaseRepository<T> {
    public T findById(long id) {
        return null;
    }
}

@ClassMetaConstants(includeInheritedMethods = true)
public class UserRepository extends BaseRepository<User> {
    public List<User> findByStatus(String status) {
        return new ArrayList<>();
    }
}

// 如启用方法签名生成，可访问继承的方法常量
```

## 常见问题

### IDE支持

为了获得最佳的IDE支持，请确保：

1. 启用了注解处理（Annotation Processing）
2. 如果使用IntelliJ IDEA，可以在设置中勾选"Enable annotation processing"

### 编译错误

如果遇到编译错误，请检查：

1. 是否正确添加了依赖
2. 是否在正确的位置添加了`annotationProcessor`配置
3. 方法签名是否包含特殊字符

## 注意事项

1. 类名常量：
  - `SIMPLE_CLASS_NAME` 为不含包名的二进制简单名；嵌套类使用 `$`（如 `Outer$Inner`）。
  - `CLASS_NAME` 为二进制全限定名，包含包与 `$`（如 `com.example.Outer$Inner`）。
  - 标准模式生成类名为 `<TypeSimpleName>_Metas`（可通过注解参数 `innerClassName` 自定义后缀）。
2. 对于泛型类，常量不包含类型参数（符合 Java 命名规范）。
3. 当启用方法签名生成与 `includeInheritedMethods=true` 时，可能生成大量常量，请谨慎使用。

## 示例：嵌套类与泛型类
```java
public class Outer {
  @ClassMetaConstants
  public static class Inner {}
}

@ClassMetaConstants
public class GenericClass<T> {}

assert "Outer$Inner".equals(Inner_Metas.SIMPLE_CLASS_NAME);
assert GenericClass_Metas.SIMPLE_CLASS_NAME.equals("GenericClass");
```

## 运行测试
- 在 `component/test` 提供了覆盖普通类、内部类、泛型类、匿名类影响的 JUnit 5 用例（`ClassNameGenerationTest`）。
- 测试断言 `SIMPLE_CLASS_NAME/CLASS_NAME` 与预期一致。

# Compile-time Code Gen 组件

一个编译时期自动生成类名和方法签名常量的注解处理器组件，类似于Lombok的@FieldNameConstants，但专注于生成类签名相关的常量。

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

### 基本使用

```java
import com.iuin.component.pluggable_annotation.compile_time_code_gen.annotation.ClassSignatureConstants;

@ClassSignatureConstants
public class UserService {
    public void createUser(String username, String password) {
        // 方法实现
    }
    
    public User findUserById(long id) {
        return null;
    }
}

// 使用生成的常量
String className = UserService.Signatures.CLASS_NAME; // "包名.UserService"
String methodSignature = UserService.Signatures.CREATE_USER; // "void createUser(String, String)"
```

## 注解参数说明

@ClassSignatureConstants 注解支持以下参数：

| 参数名 | 类型 | 默认值 | 说明 |
|-------|------|-------|------|
| asEnum | boolean | false | 是否使用枚举模式生成常量 |
| prefix | String | "" | 常量名前缀 |
| suffix | String | "" | 常量名后缀 |
| onlyPublicMethods | boolean | false | 是否只包含公共方法 |
| includeInheritedMethods | boolean | false | 是否包含继承的方法 |
| innerClassName | String | "Signatures" | 生成的内部常量类名 |

## 使用示例

### 1. 使用枚举模式

```java
@ClassSignatureConstants(asEnum = true)
public class ProductService {
    public List<Product> findAll() {
        return new ArrayList<>();
    }
}

// 使用枚举常量
String methodValue = ProductService.Signatures.FIND_ALL.getValue(); // "List<Product> findAll()"
```

### 2. 自定义前缀和后缀

```java
@ClassSignatureConstants(prefix = "META_", suffix = "_SIG")
public class OrderService {
    public void processOrder(long orderId) {
        // 方法实现
    }
}

// 使用自定义前缀后缀的常量
String methodSig = OrderService.Signatures.META_PROCESS_ORDER_SIG; // "void processOrder(long)"
```

### 3. 只包含公共方法

```java
@ClassSignatureConstants(onlyPublicMethods = true)
public class SecurityUtil {
    public static boolean isAuthenticated() {
        return false;
    }
    
    private static String getSessionId() {
        return "";
    } // 私有方法不会生成常量
}
```

### 4. 包含继承的方法

```java
public class BaseRepository<T> {
    public T findById(long id) {
        return null;
    }
}

@ClassSignatureConstants(includeInheritedMethods = true)
public class UserRepository extends BaseRepository<User> {
    public List<User> findByStatus(String status) {
        return new ArrayList<>();
    }
}

// 可以访问继承的方法常量
String inheritedMethodSig = UserRepository.Signatures.FIND_BY_ID; // "T findById(long)"
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

1. 生成的常量值包含完整的方法签名，包括返回类型、方法名和参数列表
2. 对于泛型方法，生成的签名包含泛型参数声明
3. 当使用`includeInheritedMethods=true`时，可能会生成大量常量，请谨慎使用

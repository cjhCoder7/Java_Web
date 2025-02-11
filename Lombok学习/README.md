# Java 8 使用 Lombok 的详细教程

Lombok 是一个 Java 的库，它通过注解来简化代码的编写，帮助开发者减少样板代码（例如 getter、setter、构造函数等）。Lombok 支持 Java 8 及更高版本，并且可以与主流的 IDE 配合使用，例如 IntelliJ IDEA 和 Eclipse。

## 一、安装 Lombok

### 1. 下载 Lombok

- 访问 Lombok 官方网站: <https://projectlombok.org/download>
- 下载最新版本的 Lombok JAR 文件。

### 2. 安装

- 打开命令行，运行以下命令：

    bash复制

    ```bash
    java -jar lombok.jar
    ```

- 按照提示，将 Lombok 安装到你的 IDE。

### 3. 配置 IDE

#### 对于 IntelliJ IDEA

- 打开 IntelliJ IDEA。
- 转到 `File > Settings > Plugins`，搜索 "Lombok"，并启用插件。
- 确保在项目的依赖中添加 Lombok 依赖（见下文的 Maven/Gradle 配置）。

#### 对于 Eclipse

- 将 Lombok JAR 文件拖放到 Eclipse IDE 上，安装即可。

## 二、在项目中添加 Lombok 依赖

### Maven

在 `pom.xml` 文件中添加以下依赖：

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.24</version> <!-- 请根据最新版本更新 -->
    <scope>provided</scope>
</dependency>
```

### Gradle

在 `build.gradle` 文件中添加以下依赖：

```gradle
implementation 'org.projectlombok:lombok:1.18.24' // 请根据最新版本更新
annotationProcessor 'org.projectlombok:lombok:1.18.24'
```

### 三、Lombok 的常用注解

### 1. `@Getter` 和 `@Setter`

自动生成类的 getter 和 setter 方法。

#### 示例代码

```java
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String name;
    private int age;
}
```

#### 等效于

```java
public class User {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

### 2. `@ToString`

生成类的 `toString` 方法。

#### 示例代码

```java
import lombok.ToString;

@ToString
public class User {
    private String name;
    private int age;
}
```

#### 输出

```java
User{name='Alice', age=30}
```

### 3. `@EqualsAndHashCode`

生成类的 `equals` 和 `hashCode` 方法。

#### 示例代码

```java
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class User {
    private String name;
    private int age;
}
```

#### 等效于

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return age == user.age && Objects.equals(name, user.name);
}

@Override
public int hashCode() {
    return Objects.hash(name, age);
}
```

### 4. `@NoArgsConstructor`, `@AllArgsConstructor`, `@RequiredArgsConstructor`

自动生成无参构造函数、全参构造函数和带注解字段的构造函数。

#### 示例代码

```java
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private String name;
    private final int age; // 使用 final 字段
    private String email;
}
```

#### 等效于

```java
public class User {
    private String name;
    private final int age;
    private String email;

    // 无参构造函数
    public User() {}

    // 全参构造函数
    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    // 包含 final 字段的构造函数
    public User(int age) {
        this.age = age;
    }
}
```

### 5. `@Builder`

提供流式 API 来创建对象。

#### 示例代码

```java
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private String name;
    private int age;
    private String email;
}
```

#### 使用方式

```java
User user = User.builder()
                .name("Alice")
                .age(30)
                .email("alice@example.com")
                .build();
```

### 6. `@Data`

等效于 `@Getter`、`@Setter`、`@ToString`、`@EqualsAndHashCode` 和 `@RequiredArgsConstructor` 的组合。

#### 示例代码

```java
import lombok.Data;

@Data
public class User {
    private String name;
    private int age;
    private String email;
}
```

#### 等效于

```java
public class User {
    private String name;
    private int age;
    private String email;

    // getter 和 setter 方法
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    // 其他字段同理

    // toString 方法
    @Override
    public String toString() { /* ... */ }

    // equals 和 hashCode 方法
    @Override
    public boolean equals(Object o) { /* ... */ }
    @Override
    public int hashCode() { /* ... */ }

    // 构造函数
    public User(String name, int age, String email) { /* ... */ }
}
```

### 7. `@Slf4j`/`@Log4j`

自动生成日志记录器。

#### 示例代码

```java
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Example {
    public void doSomething() {
        log.info("Doing something...");
    }
}
```

#### 等效于

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example {
    private static final Logger log = LoggerFactory.getLogger(Example.class);

    public void doSomething() {
        log.info("Doing something...");
    }
}
```

### 8. `@SneakyThrows`

用于避免显式捕获或声明异常。

#### 示例代码

```java
import lombok.SneakyThrows;

public class Example {
    @SneakyThrows
    public void readFile() {
        FileReader reader = new FileReader("file.txt");
        // ...
    }
}
```

#### 等效于

```java
public class Example {
    public void readFile() {
        try {
            FileReader reader = new FileReader("file.txt");
            // ...
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
```

### 四、注意事项

1. **IDE 配置**：确保在 IDE 中正确安装并启用 Lombok 插件，否则代码可能会显示错误。
2. **字节码增强**：Lombok 在编译时通过字节码增强工具（如 ASM）生成代码，因此在运行时不会对性能产生额外影响。
3. **与现有代码兼容**：Lombok 不会覆盖您手动编写的 getter、setter 等方法。

## 五、总结

如果需要进一步的支持，请随时参考 Lombok 的官方文档：<https://projectlombok.org/features>

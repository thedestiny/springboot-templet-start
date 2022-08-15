
```

https://juejin.cn/post/7131918421393932324

JAVA SPI = 基于接口的编程＋策略模式＋配置文件 的动态加载机制

1 JDBC加载不同类型的数据库驱动 2 日志门面接口实现类加载，SLF4J加载不同提供商的日志实现类 3 Spring中大量使用了SPI
对servlet3.0规范
对ServletContainerInitializer的实现
自动类型转换Type Conversion SPI(Converter SPI、Formatter SPI)等
当外部程序通过java.util.ServiceLoader类装载这个接口时 需要在Jar包的**META-INF/services/**目录下


```

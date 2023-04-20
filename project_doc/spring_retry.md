spring retry

```

<dependency>
    <groupId>org.springframework.retry</groupId>
    <artifactId>spring-retry</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>

SpringApplication 添加 @EnableRetry 注解


/**
  * value：抛出指定异常才会重试
  * include：和value一样，默认为空，当exclude也为空时，默认所有异常
  * exclude：指定不处理的异常
  * maxAttempts：最大重试次数，默认3次
  * backoff：重试等待策略，
  * 默认使用@Backoff，@Backoff的value默认为1000L，我们设置为2000； 以毫秒为单位的延迟（默认 1000）
  * multiplier（指定延迟倍数）默认为0，表示固定暂停1秒后进行重试，如果把multiplier设置为1.5，则第一次重试为2秒，第二次为3秒，第三次为4.5秒。
  */

 @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 1.5))
 public String test() throws InterruptedException {
     TimeUnit.MICROSECONDS.sleep(200);
     log.info("test被调用,时间：" + LocalTime.now());
     int num = 4 / 0;
     return "ss";
 }

```


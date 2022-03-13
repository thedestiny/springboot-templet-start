package com.destiny.origin.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步任务配置
 */
// 开启异步配置
@Slf4j
@EnableAsync
@Configuration
public class AppExecutorConfig implements AsyncConfigurer, SchedulingConfigurer {

    // 获取异步线程池执行器
    @Override
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 存活时间
        taskExecutor.setKeepAliveSeconds(60);
        // 核心线程数 最大线程数 核心线程超时关闭 队列最大容量
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setAllowCoreThreadTimeOut(true);
        taskExecutor.setQueueCapacity(200);
        // 线程前缀
        taskExecutor.setThreadNamePrefix("async-task");
        // 拒绝策略，直接丢弃
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        // 线程池关闭的时候等待所有任务完成后再继续销毁Bean
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 等待60s后在进行销毁
        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.initialize();
        return taskExecutor;

    }

    // 用于捕获异常信息
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        // lambda 方式创建 AsyncUncaughtExceptionHandler
        return (throwable, method, objects) ->
                System.out.println("error info ::" + throwable.getMessage() + ", method ::"
                        + method.getName() + ", parameters ::" + JSONObject.toJSONString(objects));
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        // 设置任务的定时执行任务
        registrar.setTaskScheduler(taskScheduler());

    }
    /**
     * 定时任务使用的线程池
     */
    @Bean(name = "taskScheduler", destroyMethod = "shutdown", initMethod = "initialize")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("app-schedule-");
        scheduler.setAwaitTerminationSeconds(600);
        return scheduler;
    }

    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(10);
        executor.setThreadNamePrefix("event-app-");
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        multicaster.setTaskExecutor(executor);
        return multicaster;
    }

}

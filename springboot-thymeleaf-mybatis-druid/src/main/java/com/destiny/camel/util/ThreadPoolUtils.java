package com.destiny.camel.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-05-20 10:27 AM
 */

@Slf4j
public class ThreadPoolUtils {

    private ThreadPoolUtils() {
    }

    public static ThreadPoolExecutor makeServerThreadPool(final String serverType){
        ThreadPoolExecutor serverHandlerPool = new ThreadPoolExecutor(
                60,
                300,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "xxl-rpc, "+serverType+"-serverHandlerPool-" + r.hashCode());
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        throw new RuntimeException("spring-boot "+serverType+" Thread pool is EXHAUSTED!");
                    }
                });		// default maxThreads 300, minThreads 60

        return serverHandlerPool;
    }

}

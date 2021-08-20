package com.destiny.squirrel.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-08-19 11:41 AM
 */
@Slf4j
public class ExecutorTest {
    public static void main(String[] args) {

        // CompletionService
        CompletionService service = new ExecutorCompletionService(
                new ThreadPoolExecutor(2, 3, 2,
                        TimeUnit.SECONDS, new LinkedBlockingDeque<>(34)));

        service.submit(()->{
            System.out.println("dd");
            return "";
        });



    }
}

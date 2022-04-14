package com.destiny.origin.utils;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-04-14 2:00 PM
 */
@Slf4j
public class CompUtils {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(()-> {
            log.info("supplyAsync");
            return "supplyAsync";
        });
        CompletableFuture<String> thenApply = future.thenApply((params) -> {
            log.info("thenApply supplyAsync {}", params);
            return params + " res";
        });
        log.info("thenApply result is {}",thenApply.get());


        ExecutorService service = Executors.newFixedThreadPool(3);
        Callable<String> task1 = () ->{return "33";};
        Runnable task2 = () ->{
            System.out.println("task2 ");
        };
        service.submit(task1);
        service.execute(task2);



        CompletableFuture<String> async1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(RandomUtil.randomInt(1, 4));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("async1 ... ");
            return "async1";
        }, Executors.newSingleThreadExecutor());

        CompletableFuture<String> async2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(RandomUtil.randomInt(1, 4));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("async2 ... ");
            return "async2";
        });

        // allOf 正常的话返回 null 异常的话 返回则cf4.get时会抛出异常
        // anyOf 正常的话返回 结果 异常的话 返回则cf4.get时会抛出异常
        CompletableFuture<Void> future1 = CompletableFuture.allOf(async1, async2).whenComplete((res, ex) -> {
            System.out.println("future1 " + res);
        });

        CompletableFuture<Object> future2 = CompletableFuture.anyOf(async1, async2).whenComplete((res, ex) -> {
            System.out.println("future2  " +res);
        });
        System.out.println(future1.get());
        System.out.println(future2.get());

        CompletableFuture<String> cf2 =  async1.thenCompose((params) -> {
            System.out.println("thenCompose  "  + params);
            return CompletableFuture.supplyAsync(() ->{
                log.info("thenCompose supplyAsync ");
               return "4444";
            });
        });

        log.info("info {}", cf2.get());

    }


}

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
public class CompTest {

    public void test(){
//        CompletableFuture<String> thenApply = future.thenApply((params) -> {
//            log.info("thenApply supplyAsync {}", params);
//            return params + " res";
//        });
//        CompletableFuture<String> thenApplyAsync = future.thenApplyAsync((params) -> {
//            log.info("thenApplyAsync supplyAsync {}", params);
//            return params + " res";
//        });
//        log.info("thenApply result is {}", thenApply.get());
//        log.info("thenApplyAsync result is {}", thenApplyAsync.get());
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync");
            return "supplyAsync";
        });
        CompletableFuture<Void> thenAccept = future.thenAccept((params) -> {
            log.info("thenAccept supplyAsync {}", params);
        });
        CompletableFuture<Void> thenRun = future.thenRun(() -> {
            log.info("thenRun supplyAsync ");
        });

        future.exceptionally((ex) -> {
            log.info("error information " + ex.getLocalizedMessage());
            return CompletableFuture.completedFuture(ex.getMessage());
        });

        log.info("thenApply result is {}", thenAccept.get());
        log.info("thenApplyAsync result is {}", thenRun.get());




    }


}

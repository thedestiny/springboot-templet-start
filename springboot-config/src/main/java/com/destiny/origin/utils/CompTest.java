package com.destiny.origin.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-04-14 2:00 PM
 */
@Slf4j
public class CompTest {

    public void test() {
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

//        CompletableFuture<Void> thenAccept = future.thenAccept((params) -> {
//            log.info("thenAccept supplyAsync {}", params);
//        });
//        CompletableFuture<Void> thenRun = future.thenRun(() -> {
//            log.info("thenRun supplyAsync ");
//        });
//
//        // 抛出异常信息
//        CompletableFuture<String> exceptionally = future.exceptionally((ex) -> {
//            log.info("error information " + ex.getLocalizedMessage());
//            return ex.getMessage();
//        });
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync");
            return "supplyAsync";
        });

        // 抛出异常信息
        CompletableFuture<String> exceptionally = future.exceptionally((ex) -> {
            log.info("error information " + ex.getLocalizedMessage());
            return ex.getMessage();
        });
        // 返回结果
        CompletableFuture<String> whenComplete = future.whenComplete((res, ex) -> {
            if (StrUtil.isNotBlank(res)) {
                log.info("task execute result {}", res);
            }
            if (res != null) {
                log.info("task error info {}", ex.getMessage());
            }
        });

        // future.handleAsync()

        CompletableFuture<String> thenCompose = future.thenCompose((res) -> {
            log.info("result is {}", res);
            return CompletableFuture.supplyAsync(() -> {
                log.info("supplyAsync");
                return "result";
            });
        });

        log.info("whenComplete result is {}", whenComplete.get());
        log.info("exceptionally result is {}", exceptionally.get());

        CompletableFuture.allOf(future);
        CompletableFuture.anyOf(future);


    }


}

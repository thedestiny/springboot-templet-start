package com.destiny.origin.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description
 * @Author destiny
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


//        // 返回结果
//        CompletableFuture<String> whenComplete = future.whenComplete((res, ex) -> {
//            if (StrUtil.isNotBlank(res)) {
//                log.info("task execute result {}", res);
//            }
//            if (res != null) {
//                log.info("task error info {}", ex.getMessage());
//            }
//        });
//
//        // handle 处理返回结果
//        CompletableFuture<String> handle = future.handle((res, ex) -> {
//            if (StrUtil.isNotBlank(res)) {
//                log.info("task execute result {}", res);
//                return "handle result exception";
//            }
//            if (res != null) {
//                log.info("task error info {}", ex.getMessage());
//            }
//            return "handle result";
//        });


//        // combine 合并异步任务的执行结果
//        CompletableFuture<String> combine = async1.thenCombine(async2, (res1, res2) -> {
//            log.info("combine res1 {} res2 {}", res1, res2);
//            return res1 + "-" + res2;
//        });
//        // acceptBoth 合并异步任务进行消费
//        CompletableFuture<Void> acceptBoth = async1.thenAcceptBoth(async2, (res1, res2) -> {
//            log.info("acceptBoth res1 {} res2 {}", res1, res2);
//        });
//        // runAfterBoth 合并异步任务不接受任务参数
//        CompletableFuture<Void> afterBoth = async1.runAfterBoth(async2, () -> {
//            log.info("afterBoth ----");
//        });
//
//        log.info("combine result {}", combine.get());
//        log.info("acceptBoth result {}", acceptBoth.get());
//        log.info("afterBoth result {}", afterBoth.get());

//        CompletableFuture<String> thenCompose = async1.thenCompose((result) -> {
//            log.info("thenCompose result {}", result);
//            return CompletableFuture.supplyAsync(() -> {
//                log.info("thenCompose supplyAsync {}", result);
//                return result;
//            });
//        });
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {


        CompletableFuture<String> async1 = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync1");
            return "async1";
        });

        CompletableFuture<String> async2 = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync2");
            return "async2";
        });

        // applyToEither 获取任何一个先执行完成的任务执行结果
        CompletableFuture<String> applyToEither = async1.applyToEither(async2, (res) -> {
            log.info("applyToEither res {} ", res );
            return res;
        });
        // acceptEither  获取任何一个先执行完成的任务进行消费
        CompletableFuture<Void> acceptEither = async1.acceptEither(async2, (res) -> {
            log.info("acceptEither res {} ", res);
        });
        // runAfterEither  获取任何一个先执行完成的不接受任务参数
        CompletableFuture<Void> runAfterEither = async1.runAfterEither(async2, () -> {
            log.info("runAfterEither ----");
        });

        log.info("applyToEither result {}", applyToEither.get());
        log.info("acceptEither result {}", acceptEither.get());
        log.info("runAfterEither result {}", runAfterEither.get());

    }


}

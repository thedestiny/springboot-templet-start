package com.destiny.dog.learn.concurrency;

import cn.hutool.core.thread.RejectPolicy;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.destiny.dog.learn.juc.ReentrantReadWriteLockTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.SQLOutput;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.function.*;

@Slf4j
public class CompletableFutureTest {

    /**
     * CompletableFuture 使用 ForkJoin 线程池来实现
     * N threads = N CPU * U CPU * (1 + W/C)
     * 其中：
     * N CPU 是处理器的核的数目，可以通过 Runtime.getRuntime().availableProcessors 得到
     * U CPU 是期望的CPU利用率（该值应该介于0和1之间）
     * W/C 是等待时间与计算时间的比率
     * <p>
     * 这里太啰嗦了，一般的设置线程池的大小规则是
     * 如果服务是cpu密集型的，设置为电脑的核数
     * 如果服务是io密集型的，设置为电脑的核数*2
     * <p>
     * CompletableFuture 优点
     * 异步任务结束时，会自动回调某个对象的方法；
     * 异步任务出错时，会自动回调某个对象的方法；
     * 主线程设置好回调后，不再关心异步任务的执行。
     */

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        CountDownLatch latch = new CountDownLatch(1);
        CompletableFuture<String> ft = CompletableFuture.supplyAsync(() -> {

            System.out.println("输出内容");
            log.info("输出");
            latch.countDown();
            return "44";
        });

        ft.thenAccept((res) -> {
            System.out.println("结果" + res);
        });

        ft.exceptionally(e -> {
            System.out.println(e.getMessage());
            return e.getMessage();
        });

        CompletableFuture<Integer> fff = ft.thenApplyAsync(res -> {
            System.out.println("rrr " + res);
            return 23;
        });

        fff.thenAccept(node -> {
            System.out.println("node is :" + node);
        });


        latch.await();
        System.out.println("程序结束");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(RandomUtil.randomInt(2, 4));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "cf1";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(RandomUtil.randomInt(2, 4));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "cf2";
        });

        // anyOf 任何一个有返回  allOf 所有的都返回
        // xxx()：表示该方法将继续在已有的线程中执行；
        // xxxAsync()：表示将异步在线程池中执行。

        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(cf1, cf2);

        System.out.println(anyOf.get());

        anyOf.thenAccept(nd -> {
            System.out.print(" nd is : : ");
            System.out.println(nd);
        });


        TimeUnit.SECONDS.sleep(333);


    }

    @Test
    public void test001() throws Exception {

        // runAsync 异步执行任务，无返回值
        // supplyAsync 异步执行任务，有返回值
        // thenCombine 合并已经执行的任务1和任务2，交给任务3进行执行
        // thenApply、thenAccept、thenRun 和 thenCompose 是串行关系


        // 1 异步执行任务
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            log.info("异步任务第一步 runAsync ");
        });
        // 2 异步执行任务第二步
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            log.info("异步任务第二步 runAsync ");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "第二步结果";
        });

        // 3 合并执行结果 thenCombine
        CompletableFuture<String> futureRes = future1
                .thenCombine(future2, (res1, res2) -> {
                    log.info("合并第一步和第二步结果 thenCombine ");
                    return "3:: ->" + res2;
                }).exceptionally(e -> {
                    log.info("异常信息 {}", e.getMessage());
                    return "异常信息";
                });

        log.info("最终执行结果 :: " + futureRes.get());

        // 2依赖1的结果,3依赖2的结果,123 串行执行
        CompletableFuture<String> f0 =
                CompletableFuture.supplyAsync(
                        () -> "Hello World") //1
                        .thenApply(s -> s + " QQ") //2
                        .thenApply(String::toUpperCase);//3
        System.out.println(f0.join());

        // 汇聚关系
        // thenCombine、thenAcceptBoth 和 runAfterBoth 表示 and 关系
        // applyToEither、acceptEither 和 runAfterEither 表示 or 关系，任何一个执行完都可以进行

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            log.info("异步任务第三步 runAsync ");
            return "第三步结果";
        });

        // applyToEither
        CompletableFuture<String> fun = future2.applyToEither(future3, (res) -> "applyToEither" + res);

        log.info("applyToEither join is {}", fun.join());
        log.info("applyToEither get  is {}", fun.get());

        // thenCombine 会把 两个 CompletionStage 的任务都执行完成后，把两个任务的结果一块交给 thenCombine 来处理。
        // future2.thenCombine()
		
	/*	CompletableFuture completableFuture = CompletableFuture.supplyAsync(() -> {
			log.info("supplyAsync value {}", "333");
			return "333";
		});
		CompletableFuture completableFuture1 = completableFuture.thenCompose(node -> {
			
			log.info("thenCompose value {}", node);
			return "444" + node;
		});
		
		System.out.println(completableFuture1.get());*/


    }

    @Test
    public void test004() {

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(RandomUtil.randomInt(0, 4));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "步骤一的任务完成";
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(RandomUtil.randomInt(0, 4));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "步骤二的任务完成";
        });

        CompletableFuture<String> result = f1.thenCombine(f2, (res1, res2) -> {
            try {
                TimeUnit.SECONDS.sleep(RandomUtil.randomInt(0, 4));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String detail = StrUtil.format("{} -> {} ,可以执行第三步了", res1, res2);
            return detail;
        });

        String join = result.join();
        System.out.println(join);


        CompletableFuture<String> f0 =
                CompletableFuture.supplyAsync(
                        () -> "Hello World")      // 1
                        .thenApply(s -> s + " QQ")  // 2
                        .thenApply(String::toUpperCase)
                        .exceptionally(e -> {
                            System.out.println(e.getMessage());
                            return e.getMessage();
                        });// 3

        System.out.println(f0.join());


        CompletableFuture<String> f3 =
                CompletableFuture.supplyAsync(() -> {
                    int t = RandomUtil.randomInt(1, 3);
                    try {
                        TimeUnit.SECONDS.sleep(RandomUtil.randomInt(1, 5));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return String.valueOf(t);
                });

        CompletableFuture<String> f4 =
                CompletableFuture.supplyAsync(() -> {
                    int t = RandomUtil.randomInt(0, 4);
                    try {
                        TimeUnit.SECONDS.sleep(RandomUtil.randomInt(1, 5));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return String.valueOf(t) + "f4";
                });

        CompletableFuture<String> f5 =
                f3.applyToEither(f4, s -> s);

        CompletableFuture<Object> re3 = CompletableFuture.anyOf(f3, f4);
        try {
            System.out.println(re3.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(f5.join());

        // 线程工厂
        ThreadFactory factory = (runnable) -> {
            return new Thread(runnable, "demo-server-" + runnable.hashCode());
        };
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4,
                3000, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(300), factory,
                RejectPolicy.CALLER_RUNS.getValue());

        CompletionService service = new ExecutorCompletionService(executor);


    }

    public void sleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test005() {

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            log.info("起床时刻");
            log.info("准备早餐");
            log.info("洗漱中...");
            sleep(RandomUtil.randomInt(5, 10));
            return "洗漱完成";
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            sleep(RandomUtil.randomInt(1, 2));
            log.info("加热牛奶中...");
            sleep(RandomUtil.randomInt(2, 5));
            log.info("加热面包中...");
            sleep(RandomUtil.randomInt(2, 5));
            return "早饭完成";
        });


        CompletableFuture<String> result = f1.thenCombineAsync(f2, (res1, res2) -> {
            String detail = StrUtil.format("{} -> {} ,可以准备吃饭了", res1, res2);
            log.info(detail);
            sleep(RandomUtil.randomInt(1, 2));
            return "出门上班";
        });

        String join = result.join();
        log.info(join);


        CompletableFuture<String> f3 =
                CompletableFuture.supplyAsync(() -> {
                    int t = RandomUtil.randomInt(1, 3);
                    sleep(t);
                    return "53路公交车";
                });

        CompletableFuture<String> f4 =
                CompletableFuture.supplyAsync(() -> {
                    int t = RandomUtil.randomInt(0, 4);
                    sleep(t);
                    return "89路公交车";
                });

        CompletableFuture<String> f6 =
                CompletableFuture.supplyAsync(() -> {
                    int t = RandomUtil.randomInt(1, 2);
                    sleep(t);
                    return "80路公交车";
                });


        CompletableFuture<String> re3 = CompletableFuture.anyOf(f3, f4, f6).thenApply(res -> {
            log.info("座上了 {}", res);
            sleep(RandomUtil.randomInt(3));
            int i = RandomUtil.randomInt(10);
            if (i < 2) {
                throw new RuntimeException("突然身体不舒服，去医院看病了");
            } else if (i < 4) {
                throw new RuntimeException("突然不想上班了，直接旅游去了");
            } else if (i < 6) {
                throw new RuntimeException("公交车坐反了，干脆逛街去了");
            }

            return "抵达公司,开始上班";
        }).exceptionally(ex -> {
            return ex.getMessage() + " -- ";
        }).handle((res, ex) -> {
            if (ex == null) {
                log.info("handle {}", res);
            } else {
                log.error("error {}", ex.getMessage());
            }
            return "结束";
        });

        try {
            log.info("最终结果{}", re3.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // CompletableFuture<String> f5 =
        //         f3.applyToEither(f4, s -> s);
        // System.out.println(f5.join());

        //        CompletableFuture<String> f0 =
//                CompletableFuture.supplyAsync(
//                        () -> "Hello World")      // 1
//                        .thenApply(s -> s + " QQ")  // 2
//                        .thenApply(String::toUpperCase)
//                        .exceptionally(e -> {
//                            System.out.println(e.getMessage();
//                            return e.getMessage();
//                        });// 3
//
//        System.out.println(f0.join());


    }

    @Test
    public void test006() throws ExecutionException, InterruptedException {

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            log.info("起床时刻");
            log.info("准备早餐");
            log.info("洗漱中...");
            sleep(RandomUtil.randomInt(5, 10));
            return "洗漱完成";
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            sleep(RandomUtil.randomInt(1, 2));
            log.info("加热牛奶中...");
            sleep(RandomUtil.randomInt(2, 5));
            log.info("加热面包中...");
            sleep(RandomUtil.randomInt(2, 5));
            return "早饭完成";
        });


        //
        // thenCompose
        // thenCombine
        // thenApply
        // then
        // pidstat -t -p <mysqld_pid> 1  5
        CompletableFuture<String> future = f1.thenCompose(res -> {

            return CompletableFuture.supplyAsync(() -> {
                return "dd";
            });
        });





        log.info("compose {}", future.get());
        // 将前面异步任务的结果交给后面进行执行
        CompletableFuture<String> apply = f1.thenApply(res -> {
            return "第二步" + res;
        });
        log.info("apply {}", apply.get());


        CompletableFuture<String> bus =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(RandomUtil.randomInt(2, 4));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "900公交";
                })
                        .applyToEither(CompletableFuture.supplyAsync(() -> {
                            try {
                                TimeUnit.SECONDS.sleep(RandomUtil.randomInt(1, 3));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            return "800公交";
                        }), res -> {
                            log.info("first comes 最终结果{}", res);
                            return res;
                        });

        //  https://mp.weixin.qq.com/s/PLbghC7izWKJI4W_s7iZFg

        bus.thenApply(node -> {return node + "";});


        log.info("result is {}", bus.get());

    }

    @Test
    public void test007(){

        BiConsumer<String,String> consumer = (node1, node2) -> {
            System.out.println(node1 + " : " + node2);
        };
        consumer.accept("44","55");

        BiFunction<String,String,Integer> function = (node1,node2) ->{return Integer.valueOf(node1) + Integer.valueOf(node2);};
        function.apply("34","45");

        Supplier<String> supplier = () -> {return "34";};
        System.out.println(supplier.get());

        CompletableFuture<String> temp = CompletableFuture.supplyAsync(() -> {
            return "生产一个字符串";
        });

        CompletableFuture<String> result = temp.thenApply(node -> {
            return node + " :添加后缀";
        });

        CompletableFuture<Void> future = temp.thenAccept(node -> {
            System.out.println("已经生成了" + node);
        });


        CompletableFuture<String> compose = temp.thenCompose(node -> {
            return CompletableFuture.supplyAsync(() -> {
                return node + "结果";
            });
        });

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(()->{
            return "f1";
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(()->{
            return "f2";
        });



    }

}

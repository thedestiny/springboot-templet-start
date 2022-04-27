package com.destiny.origin.utils.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-04-27 9:32 AM
 */
//   Throughput("thrpt", "Throughput, ops/time"),
//   AverageTime("avgt", "Average time, time/op"),
//   SampleTime("sample", "Sampling time"),
//   SingleShotTime("ss", "Single shot invocation time"),

// State 可以被继承使用，如果父类定义了该注解，子类则无需定义。
// Benchmark 所有的测试线程共享一个实例
// Group 同一个线程在同一个Group 里面共享实例
// Thread  默认 每个测试线程分片一个实例
// Warmup 预热 java jit 机制的存在
// Measurement 实际调用方法所需要配置的一些基本测试参数，可用于类或者方法上
// @Threads 每个锦辰中的测试线程


@BenchmarkMode(Mode.AverageTime) // 测试完成时间
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 2, timeUnit = TimeUnit.SECONDS) // 预热 1 轮，每次 2s
@Measurement(iterations = 5, time = 4, timeUnit = TimeUnit.SECONDS) // 测试 5 轮，每次 3s
@Fork(1) // fork 1 个线程
@State(Scope.Benchmark)
@Threads(1000) // 开启 1000 个并发线程
public class JmhTest001 {

    // Iteration 是 JMH 进行测试的最小单位。一次 Iteration 就是1秒钟，一秒内不断调用Benchmark方法对其进行采样
    // Warmup 需要进行预热,

    public static void main(String[] args) throws RunnerException {
        // 启动基准测试
        Options opt = new OptionsBuilder()
                .include(JmhTest001.class.getSimpleName()) // 要导入的测试类
                .build();
        new Runner(opt).run(); // 执行测试
    }

    @Param({"1", "2", "3", "4"})
    private int type;

    @Setup
    public void test() {
        switch (type) {
            case 1:
                System.out.println(1);
            default:
                System.out.println(type);
        }
    }

    @Benchmark
    public int atomicTest(Blackhole blackhole) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < 1024; i++) {
            atomicInteger.addAndGet(1);
        }
        // 为了避免 JIT 忽略未被使用的结果
        return atomicInteger.intValue();
    }

    @Benchmark
    public int longAdderTest(Blackhole blackhole) throws InterruptedException {
        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 1024; i++) {
            longAdder.add(1);
        }
        return longAdder.intValue();
    }


}

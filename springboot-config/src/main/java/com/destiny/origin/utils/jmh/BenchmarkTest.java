package com.destiny.origin.utils.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-04-26 5:16 PM
 */

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkTest {

    // https://blog.csdn.net/wangxuelei036/article/details/105240522

    public static void main(String[] args) throws Exception {
        // 使用一个单独进程执行测试，执行5遍warmup，然后执行5遍测试
        Options opt = new OptionsBuilder()
                .include(Benchmark.class.getSimpleName()).forks(1).warmupIterations(5)
                .measurementIterations(5).build();
        new Runner(opt).run();
    }

    @Setup
    public void init() {

    }

    @TearDown
    public void down() {

    }

    @Benchmark
    public void test() {

    }



}

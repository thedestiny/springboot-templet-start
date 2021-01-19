package com.destiny.camel.util.atomic;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;


@BenchmarkMode(Mode.AverageTime) // 测试完成时间
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS) // 预热 1 轮，每次 1s
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS) // 测试 5 轮，每次 3s
@Fork(1) // fork 1 个线程
@State(Scope.Benchmark)
@Threads(1000) // 开启 1000 个并发线程
public class AlibabaAtomicTest {
	
	// 需要在 cmd 控制台执行: netsh winsock reset 重置
	
	public static void main(String[] args) throws RunnerException {
		// 启动基准测试
		Options opt = new OptionsBuilder()
				.include(AlibabaAtomicTest.class.getSimpleName()) // 要导入的测试类
				.build();
		new Runner(opt).run(); // 执行测试
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

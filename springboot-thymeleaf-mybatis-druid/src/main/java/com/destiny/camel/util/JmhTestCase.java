package com.destiny.camel.util;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JmhTestCase {
	
	
	@Benchmark//对要被测试性能的代码添加注解，说明该方法是要被测试性能的
	public int sleepAWhile() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// ignore
		}
		return 0;
	}
	
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JmhTestCase.class.getSimpleName())
				.forks(1)
				.warmupIterations(3)
				.measurementIterations(3)
				.build();
		
		new Runner(opt).run();
	}
	
	
}

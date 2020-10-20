package com.destiny.camel.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;


@Slf4j
public class JavaReference {
	
	
	@Test
	public void test001() throws IOException, InterruptedException {
		// https://www.cnblogs.com/leeego-123/p/11572786.html
		// -verbose:gc -XX:+PrintGCDetails -XX:+DisableExplicitGC -XX:MaxDirectMemorySize=40M
		// -verbose:gc -XX:+PrintGCDetails -Xmx20m  -Xms20m
		// freeMemory(), totalMemory(), maxMemory()
		// System.out.println("22");
		// -verbose:gc -XX:+PrintGCDetails -Xmx20m  -Xms20m
		
		SoftReference<byte[]> softReference = new SoftReference<>(new byte[1024 * 1024 * 5]);
		// array -> soft 为强引用 soft -> new String[1024 * 1024 * 1] 为弱引用
		// 弱引用适合的应用场景为缓存
		
		byte[] strings = softReference.get();
		log.info("strings is {}",strings == null);
		System.gc();
		TimeUnit.SECONDS.sleep(5);
		
		log.info("strings is {}",softReference.get() == null);
		
		String[] dd = new String[1024 * 1024 * 15];
		
		
		
	}
	
	
}

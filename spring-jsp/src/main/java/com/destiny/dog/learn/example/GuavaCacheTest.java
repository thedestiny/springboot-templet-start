package com.destiny.dog.learn.example;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.nio.charset.Charset;

public class GuavaCacheTest {
	
	public static void main(String[] args) {
		
		
		Cache<String,String> cache = CacheBuilder.newBuilder().build();
		       cache.put("word","Hello Guava Cache");
		       System.out.println(cache.getIfPresent("word"));
		
		       //
		BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), 100000000,0.01);
		
		// 加入值
		filter.put("ddd");
		filter.put("ddd1");
		filter.put("ddd2");
		filter.put("ddd3");
		
		boolean ddd = filter.mightContain("ddd");
		System.out.println(ddd);
		
		Jedis jedis = new Jedis("192.168.1.111", 6379);
		jedis.auth("12345678");
		// 获取pipeline对象
		Pipeline pipe = jedis.pipelined();
		
		pipe.multi();
		
		pipe.discard();
		
		Transaction multi = jedis.multi();
		multi.set("dd","rrr");
		multi.exec();
		
		
	}
	
}

package com.destiny.squirrel.service.impl;

import cn.hutool.core.util.StrUtil;
import com.destiny.squirrel.service.CacheProviderService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

@Slf4j
@Service
@Qualifier("localCacheService")
public class LocalCacheProviderImpl implements CacheProviderService {
	
	private static Map<String, Cache<String, Object>> _cacheMap = Maps.newConcurrentMap();
	
	private static final long CACHE_MAXIMUM_SIZE = 100000;
	
	private static final long CACHE_MINUTE = 100;
	
	
	static {
		
		Cache<String, Object> cacheContainer = CacheBuilder.newBuilder()
				.maximumSize(CACHE_MAXIMUM_SIZE)
				.expireAfterWrite(CACHE_MINUTE, TimeUnit.MILLISECONDS)//最后一次写入后的一段时间移出
				//.expireAfterAccess(CACHE_MINUTE, TimeUnit.MILLISECONDS) //最后一次访问后的一段时间移出
				.recordStats()//开启统计功能
				.build();
		
		_cacheMap.put(String.valueOf(CACHE_MINUTE), cacheContainer);
	}
	
	/**
	 * 查询缓存
	 *
	 * @param key 缓存键 不可为空
	 **/
	public <T extends Object> T get(String key) {
		T obj = get(key, null, null, CACHE_MINUTE);
		
		return obj;
	}
	
	/**
	 * 查询缓存
	 *
	 * @param key 缓存键 不可为空
	 * @param function 如没有缓存，调用该callable函数返回对象 可为空
	 **/
	public <T extends Object> T get(String key, Function<String, T> function) {
		T obj = get(key, function, key, CACHE_MINUTE);
		
		return obj;
	}
	
	/**
	 * 查询缓存
	 *
	 * @param key 缓存键 不可为空
	 * @param function 如没有缓存，调用该callable函数返回对象 可为空
	 * @param funcParm function函数的调用参数
	 **/
	public <T extends Object, M extends Object> T get(String key, Function<M, T> function, M funcParm) {
		T obj = get(key, function, funcParm, CACHE_MINUTE);
		
		return obj;
	}
	
	/**
	 * 查询缓存
	 *
	 * @param key 缓存键 不可为空
	 * @param function 如没有缓存，调用该callable函数返回对象 可为空
	 * @param expireTime 过期时间（单位：毫秒） 可为空
	 **/
	public <T extends Object> T get(String key, Function<String, T> function, Long expireTime) {
		T obj = get(key, function, key, expireTime);
		
		return obj;
	}
	
	/**
	 * 查询缓存
	 *
	 * @param key 缓存键 不可为空
	 * @param function 如没有缓存，调用该callable函数返回对象 可为空
	 * @param funcParm function函数的调用参数
	 * @param expireTime 过期时间（单位：毫秒） 可为空
	 **/
	public <T extends Object, M extends Object> T get(String key, Function<M, T> function, M funcParm, Long expireTime) {
		T obj = null;
		if (StrUtil.isEmpty(key) == true) {
			return obj;
		}
		
		expireTime = getExpireTime(expireTime);
		
		Cache<String, Object> cacheContainer = getCacheContainer(expireTime);
		
		try {
			if (function == null) {
				obj = (T) cacheContainer.getIfPresent(key);
			} else {
				final Long cachedTime = expireTime;
				obj = (T) cacheContainer.get(key, () -> {
					T retObj = function.apply(funcParm);
					return retObj;
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	/**
	 * 设置缓存键值 直接向缓存中插入值，这会直接覆盖掉给定键之前映射的值
	 *
	 * @param key 缓存键 不可为空
	 * @param obj 缓存值 不可为空
	 **/
	public <T extends Object> void set(String key, T obj) {
		
		set(key, obj, CACHE_MINUTE);
	}
	
	/**
	 * 设置缓存键值 直接向缓存中插入值，这会直接覆盖掉给定键之前映射的值
	 *
	 * @param key 缓存键 不可为空
	 * @param obj 缓存值 不可为空
	 * @param expireTime 过期时间（单位：毫秒） 可为空
	 **/
	public <T extends Object> void set(String key, T obj, Long expireTime) {
		if (StrUtil.isEmpty(key) == true) {
			return;
		}
		
		if (obj == null) {
			return;
		}
		
		expireTime = getExpireTime(expireTime);
		
		Cache<String, Object> cacheContainer = getCacheContainer(expireTime);
		
		cacheContainer.put(key, obj);
	}
	
	/**
	 * 移除缓存
	 *
	 * @param key 缓存键 不可为空
	 **/
	public void remove(String key) {
		if (StrUtil.isEmpty(key) == true) {
			return;
		}
		
		long expireTime = getExpireTime(CACHE_MINUTE);
		
		Cache<String, Object> cacheContainer = getCacheContainer(expireTime);
		
		cacheContainer.invalidate(key);
	}
	
	/**
	 * 是否存在缓存
	 *
	 * @param key 缓存键 不可为空
	 **/
	public boolean contains(String key) {
		boolean exists = false;
		if (StrUtil.isEmpty(key) == true) {
			return exists;
		}
		
		Object obj = get(key);
		
		if (obj != null) {
			exists = true;
		}
		
		return exists;
	}
	
	private static Lock lock = new ReentrantLock();
	
	private Cache<String, Object> getCacheContainer(Long expireTime) {
		
		Cache<String, Object> cacheContainer = null;
		if (expireTime == null) {
			return cacheContainer;
		}
		
		String mapKey = String.valueOf(expireTime);
		
		if (_cacheMap.containsKey(mapKey) == true) {
			cacheContainer = _cacheMap.get(mapKey);
			return cacheContainer;
		}
		
		try {
			lock.lock();
			cacheContainer = CacheBuilder.newBuilder()
					.maximumSize(CACHE_MAXIMUM_SIZE)
					.expireAfterWrite(expireTime, TimeUnit.MILLISECONDS)//最后一次写入后的一段时间移出
					//.expireAfterAccess(CACHE_MINUTE, TimeUnit.MILLISECONDS) //最后一次访问后的一段时间移出
					.recordStats()//开启统计功能
					.build();
			
			_cacheMap.put(mapKey, cacheContainer);
			
		} finally {
			lock.unlock();
		}
		
		return cacheContainer;
	}
	
	/**
	 * 获取过期时间 单位：毫秒
	 *
	 * @param expireTime 传人的过期时间 单位毫秒 如小于1分钟，默认为10分钟
	 **/
	private Long getExpireTime(Long expireTime) {
		Long result = expireTime;
		if (expireTime == null || expireTime < CACHE_MINUTE / 10) {
			result = CACHE_MINUTE;
		}
		
		return result;
	}
}

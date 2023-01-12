package com.destiny.camel.service.impl;

import com.destiny.camel.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Description
 * @Author destiny
 * @Date 2021-05-18 9:56 AM
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private RedissonClient redissonClient;


    @Override
    public String get(String key) {

        Jedis resource = jedisPool.getResource();
        try {
            return resource.get(key);
        } catch (Exception e) {
            log.error("occurs exception ", e);
            return "";
        } finally {
            resource.close();
        }
    }

    @Override
    public String set(String key, String value, Integer expire) {
        Jedis resource = jedisPool.getResource();
        try {
            return resource.setex(key, expire, value);
        } catch (Exception e) {
            log.error("occurs exception ", e);
            return "";
        } finally {
            resource.close();
        }
    }

    @Override
    public Long incr(String key) {
        Jedis resource = jedisPool.getResource();
        try {
            return resource.incr(key);
        } catch (Exception e) {
            log.error("occurs exception ", e);
            return -1l;
        } finally {
            resource.close();
        }
    }

    /**
     * 测试布隆过滤器
     * */
    public void test001() {

        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("bloom-filter-test", new StringCodec());

        bloomFilter.tryInit(30000, 0.003);

        for (int i = 0; i < 1000; i++) {
            bloomFilter.add("瓜田李下 " + i);
        }
        System.out.println("'瓜田李下 1'是否存在：" + bloomFilter.contains("瓜田李下 " + 1));
        System.out.println("'海贼王'是否存在：" + bloomFilter.contains("海贼王"));
        System.out.println("预计插入数量：" + bloomFilter.getExpectedInsertions());
        System.out.println("容错率：" + bloomFilter.getFalseProbability());
        System.out.println("hash函数的个数：" + bloomFilter.getHashIterations());
        System.out.println("插入对象的个数：" + bloomFilter.count());


    }




}

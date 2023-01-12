package com.destiny.camel.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description
 * @Author destiny
 * @Date 2021-05-18 10:04 AM
 */

@Slf4j
@Configuration
public class RedisConfig {

    @Autowired
    private RedisProperties properties;

    @Bean
    public RedissonClient redissonClient() {
        String address = StrUtil.format("redis://{}:{}", properties.getHost(), properties.getPort());

        Config config = new Config();
        config.useSingleServer()
                .setAddress(address)
                .setPassword(properties.getPassword())
                .setKeepAlive(true)
                // .setReconnectionTimeout(10000)
                .setRetryInterval(5000)
                .setTimeout(10000)
                .setConnectTimeout(10000);
        return Redisson.create(config);
    }

    @Bean
    public JedisPool jedisPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();

        //是否启用后进先出, 默认true
        config.setLifo(true);
        //最大空闲连接数, 默认8个
        config.setMaxIdle(8);
        //最大连接数, 默认8个
        config.setMaxTotal(8);
        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        config.setMaxWaitMillis(-1);
        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        config.setMinEvictableIdleTimeMillis(1800000);
        //最小空闲连接数, 默认0
        config.setMinIdle(0);
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        config.setNumTestsPerEvictionRun(3);
        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        config.setSoftMinEvictableIdleTimeMillis(1800000);
        //在获取连接的时候检查有效性, 默认false
        config.setTestOnBorrow(false);
        //在空闲时检查有效性, 默认false
        config.setTestWhileIdle(false);
        // String host, int port, int timeout, String password, boolean ssl
        // String host, int port, int timeout, String password, int database, boolean ssl
        return new JedisPool(config, properties.getHost(), properties.getPort(), (int) properties.getTimeout().getSeconds(), properties.getPassword(), properties.getDatabase(), true);

    }


}

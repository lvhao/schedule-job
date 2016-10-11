package com.github.schedulejob.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis 配置
 *
 * Created by root on 2016/8/14 0014.
 */
@Configuration
public class RedisConfig {
    private static final String POOL_CONFIG_PREFIX = "cache.redis.pool";
    private static final String REDIS_CONFIG_PREFIX = "cache.redis";

    @Bean
    @ConfigurationProperties(prefix=POOL_CONFIG_PREFIX)
    public JedisPoolConfig jedisPoolConfig(){
        return new JedisPoolConfig();
    }

    @Bean
    @ConfigurationProperties(prefix=REDIS_CONFIG_PREFIX)
    public JedisConnectionFactory jedisConnectionFactory(){
        return new JedisConnectionFactory();
    }

    @Bean
    public StringRedisTemplate redisTemplate(){
        StringRedisTemplate template = new StringRedisTemplate();
        JedisConnectionFactory jcf = jedisConnectionFactory();
        jcf.setPoolConfig(jedisPoolConfig());
        template.setConnectionFactory(jcf);
        return template;
    }
}

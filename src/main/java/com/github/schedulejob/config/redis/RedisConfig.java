package com.github.schedulejob.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redis 配置
 *
 * Created by root on 2016/8/14 0014.
 */
@Configuration
public class RedisConfig {

    // TODO jar maven 都拉不下来。。。
    @Bean
    public JedisPoolConfig poolConfig(){
        return new JedisPoolConfig();
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        return JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate redisTemplate(){
        return RedisTemplate();
    }
}

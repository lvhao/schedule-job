package com.github.schedulejob.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

/**
 * Redis 配置
 *
 * Created by root on 2016/8/14 0014.
 */
@Configuration
public class RedisConfig {
    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);
    private static final String REDIS_CONFIG = "/config/redis.yml";
    private static final String REDIS_CONFIG_PREFIX = "cache.redis";

    @Bean
    public MutablePropertyValues redisPropertyValues() {
        Resource resource = new ClassPathResource(REDIS_CONFIG);
        YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
        MapPropertySource propertySource = null;
        try {
            propertySource = (MapPropertySource)yamlPropertySourceLoader.load("redis_config",resource,null);
        } catch (IOException e) {
            log.error("读取redis配置文件出错!",e);
        }
        MutablePropertyValues mpv = new MutablePropertyValues();
        String[] nameKs = propertySource.getPropertyNames();
        for (String k : nameKs) {
            Object v = propertySource.getProperty(k);
            mpv.add(k,v);
        }
        return mpv;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig(){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        MutablePropertyValues redisProps = redisPropertyValues();
        RelaxedDataBinder rdb = new RelaxedDataBinder(jedisPoolConfig,REDIS_CONFIG_PREFIX);
        rdb.setIgnoreInvalidFields(true);
        rdb.bind(redisProps);
        return jedisPoolConfig;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jcf = new JedisConnectionFactory();
        jcf.setUsePool(true);
//        jcf.setHostName();
//        jcf.setPort();
        jcf.setDatabase(0);
        jcf.setPoolConfig(jedisPoolConfig());
        return jcf;
    }

    @Bean
    public StringRedisTemplate redisTemplate(){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}

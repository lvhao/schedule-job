package com.github.schedulejob.util;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis测试
 *
 * @author: lvhao
 * @since: 2016-10-11 21:13
 */
public class RedisTest extends BaseTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void addV(){
        stringRedisTemplate.opsForValue().set("nowTest","hello world");
        String v = stringRedisTemplate.opsForValue().get("nowTest");
        System.out.println("v = " + v);
    }
}

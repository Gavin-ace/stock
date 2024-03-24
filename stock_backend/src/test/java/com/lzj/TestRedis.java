package com.lzj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class TestRedis {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test01(){
        redisTemplate.opsForValue().set("myname","zhangsan");
        String myname=redisTemplate.opsForValue().get("myname");

        System.out.println(myname);
    }
}

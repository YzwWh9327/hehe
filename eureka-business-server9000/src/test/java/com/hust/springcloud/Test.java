package com.hust.springcloud;

import com.hust.springcloud.util.RedisUtil;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Resource
    private RedisUtil redisUtil;

    @org.junit.Test
    public void set(){
        String key = "1";
        String value ="123";
        boolean res = redisUtil.set(key, value, 5);
        System.out.println(res);
    }
}

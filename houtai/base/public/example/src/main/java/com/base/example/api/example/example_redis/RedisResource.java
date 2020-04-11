package com.base.example.api.example.example_redis;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 运行该例子需要先启动redis，不启动也可以，不影响springboot启动，只是调用时报错
 *
 * @author:小M
 * @date:2018/11/5 上午12:56
 */
@RestController
@RequestMapping("/redis/resource")
@ConditionalOnExpression("${srping.redis.launch:false}")
public class RedisResource {

    /**
     * RedisTemplate 是spring帮我们注入的，我们不需要@Bean
     * 详见RedisAutoConfiguration
     */
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LockUtil lockUtil;

    @ApiOperation(value = "插入KV并查询出来" , notes = "插入KV并查询出来")
    @RequestMapping(value = "/insertKV/{k}/{v}" , method = RequestMethod.POST)
    public String insertKV(@PathVariable String k , @PathVariable String v) {
        redisTemplate.opsForValue().set(k , v);
        return "success value = " + redisTemplate.opsForValue().get(k).toString();
    }


    @ApiOperation(value = "锁" , notes = "锁")
    @RequestMapping(value = "/lock/{k}" , method = RequestMethod.POST)
    public String lock(@PathVariable String k) throws Exception{
        lockUtil.lock(k);
        System.out.println("第一次锁");
        String msg = "";
        try {
            System.out.println("准备第二次锁");
            lockUtil.lock(k);
            System.out.println("第二次锁");
        }catch (Exception e) {
            msg = e.getMessage();
        }
        return "success 锁后尝试再次锁，抛异常 = " + msg;
    }



}

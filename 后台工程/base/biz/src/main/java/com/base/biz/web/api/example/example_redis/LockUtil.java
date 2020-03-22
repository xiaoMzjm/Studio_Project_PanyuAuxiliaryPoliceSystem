package com.base.biz.web.api.example.example_redis;

import java.util.Collections;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * https://www.cnblogs.com/linjiqin/p/8003838.html
 * @author:小M
 * @date:2018/5/1 2:24
 */
public class LockUtil {

    /**
     * 加锁时间，3秒过期自动释放
     */
    private final static Integer LOCK_TIMEOUT = 15000;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    private JedisPool jedisPool;

    /**
     * 锁住某个K，如果锁失败，立刻抛异常
     * 锁成功的话，默认15秒自动释放
     * @param lockKey
     * @return
     * @throws Exception
     */
    public String lock(String lockKey) throws Exception{
        String requestId = UUID.randomUUID().toString();
        Jedis jedis = jedisPool.getResource();
        this.lock(jedis , lockKey , requestId , LOCK_TIMEOUT , null);
        return requestId;
    }

    /**
     * 锁住某个K，timeout后如果还没锁成功，抛异常
     * 锁成功的话，默认15秒自动释放
     * @param lockKey
     * @param timeout   单位ms
     * @return
     * @throws Exception
     */
    public String lock(String lockKey, Long timeout) throws Exception {
        String requestId = UUID.randomUUID().toString();
        this.lock(jedisPool.getResource() , lockKey , requestId , LOCK_TIMEOUT , timeout);
        return requestId;
    }


    public void unlock(String lockKey,String requireId) {
        this.unlock(jedisPool.getResource() , lockKey , requireId);
    }

    /**
     *
     * @param jedis
     * @param lockKey       要锁住的key
     * @param requestId     加锁的客户端，防止被其他客户端解锁
     * @param expireTime    锁的过期时间，防止死锁·
     * @param timeout       尝试多少时间后，还是加锁失败的话，抛异常
     * @throws Exception
     */
    private void lock(Jedis jedis, String lockKey, String requestId, int expireTime , Long timeout) throws Exception {

        Long timeCount = 0L;
        while (true) {

            System.out.println("准备加锁");

            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

            System.out.println("加锁结束");

            // 加锁成功
            if (LOCK_SUCCESS.equals(result)) {
                return ;
            }

            if(timeout == null || (timeout != null && timeCount >= timeout)) {
                throw new RuntimeException(String.format("redis lock timeout (%sms) , lockKey=%s , requestId=%s," , timeout , lockKey , requestId));
            }

            Thread.sleep(100);
            timeCount += 100;
        }
    }

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    private void unlock(Jedis jedis, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return ;
        }
        throw new RuntimeException(String.format("releaseDistributedLock fail , lockKey=%s , requestId=%s" , lockKey , requestId));

    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}

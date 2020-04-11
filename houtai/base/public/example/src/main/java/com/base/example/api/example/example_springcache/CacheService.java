package com.base.example.api.example.example_springcache;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author:小M
 * @date:2018/11/4 下午11:59
 */
@Service
public class CacheService {

    @CachePut(value="cacheDO" , key="#cacheDO.id")
    public CacheDO insert(CacheDO cacheDO) {
        System.out.println("插入一条记录：" + cacheDO.toString());
        return cacheDO;
    }

    @Cacheable(value="cacheDO" , key="#id")
    public CacheDO findById(Long id) {
        System.out.println("根据id查询，id=" + id);
        return new CacheDO();
    }
}

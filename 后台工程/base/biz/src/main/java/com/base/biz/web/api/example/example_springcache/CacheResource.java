package com.base.biz.web.api.example.example_springcache;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:小M
 * @date:2018/11/5 上午12:03
 */
@RestController
@RequestMapping("/cache/resource")
public class CacheResource {

    @Autowired
    private CacheService cacheService;

    @ApiOperation(value = "插入一行记录" , notes = "插入一行记录")
    @RequestMapping(value = "/insert/{name}" , method = RequestMethod.POST)
    public String insert(@PathVariable String name){

        CacheDO cacheDO = new CacheDO();
        cacheDO.setId(1L);
        cacheDO.setName(name);
        cacheService.insert(cacheDO);

        return "insert " + cacheDO.toString() ;
    }

    @ApiOperation(value = "根据id查询" , notes = "根据id查询")
    @RequestMapping(value = "/findById/{id}" , method = RequestMethod.POST)
    public String findById(@PathVariable Long id){

        CacheDO cacheDO = cacheService.findById(id);

        return "result " + cacheDO.toString() ;
    }

}

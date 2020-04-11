package com.base.example.api.example.example_springcache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 *
 * 需要启动redis，不启动时系统能跑起来但是调用时抛异常
 *
 * spring cache有点小复杂，不太建议使用，代码显示编程使用redis比较明朗一点
 *
 * @author:小M
 * @date:2018/11/5 上午12:14
 */
@Configuration
@EnableCaching
public class CacheConfig {


}

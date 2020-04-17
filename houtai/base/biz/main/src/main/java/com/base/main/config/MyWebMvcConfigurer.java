package com.base.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author:小M
 * @date:2020/4/17 2:46 AM
 */
@Configuration
public class MyWebMvcConfigurer extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String diskUrl = System.getProperty("ResourceStaticUrl");
        if (StringUtils.isEmpty(diskUrl)) {
            throw new RuntimeException("ResourceStaticUrl is null");
        }
        //配置静态资源处理
        registry.addResourceHandler("/static/**")
            .addResourceLocations("file:" + diskUrl);

        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
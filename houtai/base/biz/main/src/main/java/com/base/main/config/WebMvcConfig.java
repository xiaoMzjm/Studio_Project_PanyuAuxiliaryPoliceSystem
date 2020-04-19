package com.base.main.config;

import com.base.user.server.filter.TokenInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author:小M
 * @date:2020/4/17 2:46 AM
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${ResourceStaticUrl}")
    private String diskUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (StringUtils.isEmpty(diskUrl)) {
            throw new RuntimeException("ResourceStaticUrl is null");
        }
        //配置静态资源处理
        registry.addResourceHandler("/static/**")
            .addResourceLocations("file:" + diskUrl);


        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**").
            addResourceLocations("classpath:/static/");
    }

    @Bean
    public TokenInterceptor tokenInterceptor(){
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(tokenInterceptor());
        interceptorRegistration.addPathPatterns("/**")
            .excludePathPatterns("/swagger-resources/**")
            .excludePathPatterns("/webjars/**")
            .excludePathPatterns("/v2/**")
            .excludePathPatterns("/swagger-ui.html/**")
            .excludePathPatterns("/csrf")
            .excludePathPatterns("/error");

    }

}

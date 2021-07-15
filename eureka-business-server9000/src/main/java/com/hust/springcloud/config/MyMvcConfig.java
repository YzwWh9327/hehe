package com.hust.springcloud.config;

import com.hust.springcloud.interceptor.PassportInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    // 将登录拦截器配置到容器中
    @Resource
    private PassportInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor)
                .addPathPatterns("/user/*")
                .addPathPatterns("/account/*")
                .excludePathPatterns("/user/login");
    }

}

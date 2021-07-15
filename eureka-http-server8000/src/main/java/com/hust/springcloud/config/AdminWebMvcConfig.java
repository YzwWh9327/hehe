package com.hust.springcloud.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.form.spring.SpringFormEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;


@Configuration
@Slf4j
public class AdminWebMvcConfig implements WebMvcConfigurer {
    @Bean
    public SpringFormEncoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                //1.RequestContextHolder拿到当前请求的数据，相当与拿到controller入参的HttpServletRequest
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                //老请求
                HttpServletRequest request = requestAttributes.getRequest();

                String header_value = request.getHeader("ticket");
                requestTemplate.header("ticket",header_value);
            }
        };
    }
}

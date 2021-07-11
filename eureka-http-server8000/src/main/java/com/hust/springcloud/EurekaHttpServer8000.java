package com.hust.springcloud;

import com.hust.springcloud.config.AdminWebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableFeignClients
@Import({AdminWebMvcConfig.class})
public class EurekaHttpServer8000 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaHttpServer8000.class,args);
    }
}

package com.hust.springcloud.service;

import com.hust.springcloud.common.Result;
import com.hust.springcloud.config.FeignMultipartConfig;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Service
@FeignClient(value = "EUREKA-BUSINESS-SERVER",configuration = FeignMultipartConfig.class)
public interface AccountFeignService {

    @PostMapping(value = "/account/upload" ,consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    Result upload(@RequestPart(value = "file") MultipartFile file);

    @GetMapping("/account/list")
    Result list(@RequestParam("accountName")String accountName);

    @GetMapping(value = "/account/download")
    Response download(@RequestParam("min_create_date")String min_create_date, @RequestParam("max_create_date")String max_create_date, @RequestParam("response") HttpServletResponse response);
}

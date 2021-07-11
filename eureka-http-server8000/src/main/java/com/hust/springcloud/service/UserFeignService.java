package com.hust.springcloud.service;

import com.hust.springcloud.common.Result;
import com.hust.springcloud.config.AdminWebMvcConfig;
import com.hust.springcloud.entity.UserVO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@FeignClient(value = "EUREKA-BUSINESS-SERVER",configuration = AdminWebMvcConfig.class)
public interface UserFeignService {

    @PostMapping("/user/login")
    Result login(@RequestBody UserVO userVO, @RequestParam("request")HttpServletRequest request, @RequestParam("response")HttpServletResponse response);

    @GetMapping("/user/logout")
    Result logout(@RequestHeader(value = "ticket",required = false)String ticket);

    @PutMapping("/user/modify")
    Result modify(@RequestHeader(value = "ticket",required = false)String ticket, @RequestParam("nickname")String nickname,@RequestParam("picture")String picture);
}

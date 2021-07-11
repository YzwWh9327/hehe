package com.hust.springcloud.service;

import com.hust.springcloud.common.Result;
import com.hust.springcloud.entity.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Service
@FeignClient(value = "EUREKA-BUSINESS-SERVER")
public interface UserFeignService {

    @PostMapping("/user/login")
    Result login(@RequestBody UserVO userVO,@RequestParam("response")HttpServletResponse response);

    @GetMapping("/user/logout")
    Result logout(@CookieValue(value = "ticket",required = false) String ticket);

    @PutMapping("/user/modify")
    Result modify(@CookieValue(value = "ticket",required = false) String ticket, @RequestParam("nickname")String nickname,@RequestParam("picture")String picture);
}

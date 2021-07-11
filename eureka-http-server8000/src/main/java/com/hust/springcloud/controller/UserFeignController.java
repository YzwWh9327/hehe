package com.hust.springcloud.controller;

import com.hust.springcloud.common.Result;
import com.hust.springcloud.entity.UserVO;
import com.hust.springcloud.service.UserFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("consumer")
public class UserFeignController {

    @Resource
    private UserFeignService userFeignService;

    @PostMapping("login")
    public Result login(@RequestBody UserVO userVO,
                        HttpServletResponse response){
       return userFeignService.login(userVO,response);
    }

    @GetMapping("logout")
    public Result logout(@CookieValue(value = "ticket",required = false) String ticket){
        return userFeignService.logout(ticket);
    }

    @PutMapping("modify")
    public Result modify(@CookieValue(value = "ticket",required = false) String ticket,
                         @RequestParam("nickname")String nickname,
                         @RequestParam("picture")String picture){
        return userFeignService.modify(ticket,nickname,picture);
    }
}

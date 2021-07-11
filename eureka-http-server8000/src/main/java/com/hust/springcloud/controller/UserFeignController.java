package com.hust.springcloud.controller;

import com.hust.springcloud.common.Result;
import com.hust.springcloud.entity.UserVO;
import com.hust.springcloud.service.UserFeignService;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@Slf4j
@RequestMapping("consumer")
public class UserFeignController {

    @Resource
    private UserFeignService userFeignService;

    @PostMapping("login")
    public Result login(@RequestBody UserVO userVO,
                        HttpServletRequest request,
                        HttpServletResponse response){
        return userFeignService.login(userVO, request, response);
    }

    @GetMapping("logout")
    public Result logout(@RequestHeader(value = "ticket",required = false) String ticket){
        return userFeignService.logout(ticket);
    }

    @PutMapping("modify")
    public Result modify(@RequestHeader(value = "ticket",required = false) String ticket,
                         @RequestParam("nickname")String nickname,
                         @RequestParam("picture")String picture){
        return userFeignService.modify(ticket,nickname,picture);
    }
}

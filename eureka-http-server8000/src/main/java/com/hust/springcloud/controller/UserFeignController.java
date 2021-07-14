package com.hust.springcloud.controller;

import com.hust.springcloud.common.ResponseEnum;
import com.hust.springcloud.common.Result;
import com.hust.springcloud.entity.UserVO;
import com.hust.springcloud.exception.Assert;
import com.hust.springcloud.service.UserFeignService;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("consumer")
@CrossOrigin
public class UserFeignController {

    @Resource
    private UserFeignService userFeignService;

    @PostMapping("login")
    public Result login(@RequestBody UserVO userVO,
                        HttpServletRequest request,
                        HttpServletResponse response){
//        Response rs = userFeignService.login(userVO, request, response);
//        Assert.notNull(rs, ResponseEnum.LOGIN_AUTH_ERROR);
//
//        Map<String, Collection<String>> headers = rs.headers();
//        Collection<String> list = headers.get("ticket");
//        final Iterator<String> iterator = list.iterator();
//        while (iterator.hasNext()){
//            response.addHeader("ticket",iterator.next());
//        }
        return userFeignService.login(userVO, request, response);
//        return Result.ok().message("登录成功！！！");
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

package com.hust.springcloud.controller;

import com.hust.springcloud.common.ResponseEnum;
import com.hust.springcloud.common.Result;
import com.hust.springcloud.entity.User;
import com.hust.springcloud.entity.UserVO;
import com.hust.springcloud.exception.Assert;
import com.hust.springcloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserVO userVO,
                        HttpServletResponse response){
        String username = userVO.getUsername();
        String password = userVO.getPassword();
        Assert.notEmpty(username, ResponseEnum.USER_NULL_ERROR);
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);

        Map<String, Object> map = userService.login(userVO);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath("/");
            cookie.setMaxAge(3600*24);
            response.addCookie(cookie);
            return Result.ok().data("user",(User)map.get("user"));
        }
        return Result.error().message("登录失败");
    }

    @GetMapping("/logout")
    public Result logout(@CookieValue(value = "ticket",required = false) String ticket){
        userService.logout(ticket);
        return Result.ok().message("登出成功");
    }

    @PutMapping("/modify")
    public Result modify(@CookieValue(value = "ticket",required = false) String ticket,
                         @RequestParam("nickname")String nickname,
                         @RequestParam("picture")String picture){
        User user = userService.modify(ticket,nickname,picture);
        return Result.ok().data("修改信息",user);
    }
}

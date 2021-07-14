package com.hust.springcloud.controller;

import com.hust.springcloud.common.ResponseEnum;
import com.hust.springcloud.common.Result;
import com.hust.springcloud.entity.LoginTicket;
import com.hust.springcloud.entity.User;
import com.hust.springcloud.entity.UserVO;
import com.hust.springcloud.exception.Assert;
import com.hust.springcloud.mapper.TicketMapper;
import com.hust.springcloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private TicketMapper ticketMapper;

    @PostMapping("/login")
    public Result login(@RequestBody UserVO userVO,
                        HttpServletRequest request,
                        HttpServletResponse response){
        String token = request.getHeader("ticket");
        if(token!=null){
            Integer ticketId = ticketMapper.selectByTicketInfo(token);
            LoginTicket loginTicket = ticketMapper.selectById(ticketId);
            if (loginTicket != null && loginTicket.getExpired().after(new Date()) && loginTicket.getDeleted().intValue()==0) {
                return Result.ok().message("登录成功~~~");
            }
        }
        String username = userVO.getUsername();
        String password = userVO.getPassword();
        Assert.notEmpty(username, ResponseEnum.USER_NULL_ERROR);
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);

        Map<String, Object> map = userService.login(userVO);
//        if (map.containsKey("ticket")) {
//            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
//            cookie.setPath("/");
//            cookie.setMaxAge(3600*24);
//            response.addCookie(cookie);
//            return Result.ok().data("user",(User)map.get("user"));
//        }
        response.addHeader("ticket",map.get("ticket").toString());
        return Result.ok().data("user",(User)map.get("user"));
    }

//    @GetMapping("/logout")
//    public Result logout(@CookieValue(value = "ticket",required = false) String ticket){
//        userService.logout(ticket);
//        return Result.ok().message("登出成功");
//    }
    @GetMapping("/logout")
    public Result logout(@RequestHeader(value = "ticket",required = false) String ticket){
        userService.logout(ticket);
        return Result.ok().message("登出成功");
    }
//
//    @PutMapping("/modify")
//    public Result modify(@CookieValue(value = "ticket",required = false) String ticket,
//                         @RequestParam("nickname")String nickname,
//                         @RequestParam("picture")String picture){
//        User user = userService.modify(ticket,nickname,picture);
//        return Result.ok().data("修改信息",user);
//    }
    @PutMapping("/modify")
    public Result modify(@RequestHeader(value = "ticket",required = false) String ticket,
                         @RequestParam("nickname")String nickname,
                         @RequestParam("picture")String picture){
        User user = userService.modify(ticket,nickname,picture);
        return Result.ok().data("修改信息",user);
    }
}

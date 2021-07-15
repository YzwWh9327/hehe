package com.hust.springcloud.controller;

import com.hust.springcloud.common.ResponseEnum;
import com.hust.springcloud.common.Result;
import com.hust.springcloud.entity.User;
import com.hust.springcloud.entity.UserVO;
import com.hust.springcloud.exception.Assert;
import com.hust.springcloud.mapper.TicketMapper;
import com.hust.springcloud.service.TicketService;
import com.hust.springcloud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
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
    @Resource
    private TicketService ticketService;

    @PostMapping("/login")
    public Result login(@RequestBody UserVO userVO,
                        HttpServletRequest request,
                        HttpServletResponse response){
        String token = request.getHeader("ticket");
        if(token!=null){
//            Integer ticketId = ticketMapper.selectByTicketInfo(token);
//            LoginTicket loginTicket = ticketMapper.selectById(ticketId);
//            if (loginTicket != null && loginTicket.getExpired().after(new Date()) && loginTicket.getDeleted().intValue()==0) {
//                return Result.ok().message("登录成功~~~");
//            }
            Integer ticketId = ticketService.selectByTicketInfo(token);
            if (ticketId!=null){
                return Result.ok().message("登录成功~~~");
            }

        }
        String username = userVO.getUsername();
        String password = userVO.getPassword();
        Assert.notEmpty(username, ResponseEnum.USER_NULL_ERROR);
        Assert.notEmpty(password, ResponseEnum.PASSWORD_NULL_ERROR);

        Map<String, Object> map = userService.login(userVO);
        String ticket = map.get("ticket").toString();
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("user",(User)map.get("user"));
        resMap.put("ticket",ticket);
        return Result.ok().data("body",resMap);
    }

    @GetMapping("/logout")
    public Result logout(@RequestHeader(value = "ticket",required = false) String ticket){
        userService.logout(ticket);
        return Result.ok().message("登出成功");
    }

    @PutMapping("/modify")
    public Result modify(@RequestHeader(value = "ticket",required = false) String ticket,
                         @RequestParam("nickname")String nickname,
                         @RequestParam("picture")String picture){
        User user = userService.modify(ticket,nickname,picture);
        return Result.ok().data("修改信息",user);
    }
}

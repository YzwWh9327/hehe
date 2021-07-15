package com.hust.springcloud.service;

import cn.hutool.core.lang.UUID;
import com.hust.springcloud.common.ResponseEnum;
import com.hust.springcloud.entity.LoginTicket;
import com.hust.springcloud.entity.User;
import com.hust.springcloud.entity.UserVO;
import com.hust.springcloud.exception.Assert;
import com.hust.springcloud.mapper.TicketMapper;
import com.hust.springcloud.mapper.UserMapper;
import com.hust.springcloud.util.RedisUtil;
import com.hust.springcloud.utils.MD5;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private TicketMapper ticketMapper;
    @Resource
    private TicketService ticketService;
    /**
     * 用户登录
     * @param userVO
     * @return
     */
    private final String SALT = "shopeeDigitalBank";
    public Map<String,Object> login(UserVO userVO) {
        String username = userVO.getUsername();
        String password = userVO.getPassword();
        User user = userMapper.selectByUserName(username);
        Assert.notNull(user, ResponseEnum.USER_NULL_ERROR);
        char[] salts = SALT.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < salts.length; i++) {
            if(i%2==0){
                buffer.append(salts[i]);
            }
        }
        String new_Salt = buffer.toString();
        String pw_md5 = MD5.encrypt(password+new_Salt);
        Assert.equals(pw_md5,user.getPassword(),ResponseEnum.LOGIN_PASSWORD_ERROR);

        Map<String,Object> result = new HashMap<>();
        String ticket = addLoginTicket(user.getId());
        result.put("user",user);
        result.put("ticket",ticket);
        return result;
    }

    private String addLoginTicket(Integer userId) {
        String ticket = UUID.randomUUID().toString().replaceAll("-", "");
        String res = ticketService.insertTicket(ticket, userId);
        return res;
    }

    /**
     * 用户退出
     * @param ticketInfo
     */
    public void logout(String ticketInfo){
        Assert.notNull(ticketInfo,ResponseEnum.LOGIN_AUTH_ERROR);
//        Integer ticketId = ticketMapper.selectByTicketInfo(ticketInfo);
//        int res = ticketMapper.deleteById(ticketId);
//        Assert.isTrue(res!=0,ResponseEnum.LOGIN_AUTH_ERROR);
        Boolean res = ticketService.deleteByTicketInfo(ticketInfo);
        Assert.isTrue(res,ResponseEnum.LOGIN_AUTH_ERROR);
    }

    /**
     * 修改profile
     */
    public User modify(String ticket, String nickname, String picture) {
        Assert.notNull(ticket,ResponseEnum.LOGIN_AUTH_ERROR);
//        Integer integer = ticketMapper.selectByTicketInfo(ticket);
//        LoginTicket loginTicket = ticketMapper.selectById(integer);
//        User user = userMapper.selectById(loginTicket.getUserId());
        Integer userId = ticketService.selectByTicketInfo(ticket);
        Assert.notNull(userId,ResponseEnum.LOGIN_AUTH_ERROR);
        User user = userMapper.selectById(userId);
        user.setNickname(nickname);
        user.setPicture(picture);
        int res = userMapper.updateById(user);
        Assert.isTrue(res!=0,ResponseEnum.USER_PROFILE_MODIFY_ERROR);
        return user;
    }
}

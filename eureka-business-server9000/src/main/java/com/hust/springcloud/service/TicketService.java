package com.hust.springcloud.service;

import com.hust.springcloud.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TicketService {
    private final Long EXPIRED_TIME_OUT = 3600l;
    @Resource
    private RedisUtil redisUtil;

    public Integer selectByTicketInfo(String ticketInfo){
        String str =(String) redisUtil.get(ticketInfo);
        if(str==null){
            return null;
        }
        return Integer.parseInt(str);
    }

    public String insertTicket(String ticket,Integer userId){
        boolean res = redisUtil.set(ticket,Integer.toString(userId),EXPIRED_TIME_OUT);
        if(res){
            return ticket;
        }
        return null;
    }
    public Boolean deleteByTicketInfo(String ticketInfo){
        return redisUtil.del(ticketInfo);
    }
}

package com.hust.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hust.springcloud.entity.LoginTicket;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TicketMapper extends BaseMapper<LoginTicket> {
    public Integer selectByTicketInfo(String ticketInfo);
}

package com.hust.springcloud.interceptor;

import cn.hutool.json.JSONObject;
import com.hust.springcloud.common.ResponseEnum;
import com.hust.springcloud.entity.LoginTicket;
import com.hust.springcloud.exception.Assert;
import com.hust.springcloud.mapper.TicketMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;


/**
 * Created by nowcoder on 2016/7/3.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Resource
    private TicketMapper ticketMapper;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        ticket = httpServletRequest.getHeader("ticket");
        if (ticket != null) {
            Integer ticketId = ticketMapper.selectByTicketInfo(ticket);
            LoginTicket loginTicket = ticketMapper.selectById(ticketId);
            if (loginTicket != null && loginTicket.getExpired().after(new Date()) && loginTicket.getDeleted().intValue()==0) {
                return true;
            }
        }
        toFontJson(httpServletResponse);
        return false;
    }
    private void toFontJson(HttpServletResponse response) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject res = new JSONObject();
        res.put("code",-201);
        res.put("msg","未登录");
        try {
            writer = response.getWriter();
            writer.print(res.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}

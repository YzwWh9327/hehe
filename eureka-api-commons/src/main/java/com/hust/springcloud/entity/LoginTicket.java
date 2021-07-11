package com.hust.springcloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "ticket")
public class LoginTicket {
    private Integer id;
    private Integer userId;
    private Date expired;
    private String ticketInfo;

    @TableLogic
    @TableField(value = "is_deleted")
    private Integer deleted;
}

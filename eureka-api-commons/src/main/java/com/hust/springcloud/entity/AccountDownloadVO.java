package com.hust.springcloud.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class AccountDownloadVO {
    private String accountName;
    private String name;
    private Gender gender;
    private Date createTime;
    private Date updateTime;
}

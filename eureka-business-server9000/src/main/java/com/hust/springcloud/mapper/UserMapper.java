package com.hust.springcloud.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hust.springcloud.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    public User selectByUserName(String username);
}

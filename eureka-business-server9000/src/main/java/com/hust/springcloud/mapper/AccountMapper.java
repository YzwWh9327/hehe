package com.hust.springcloud.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hust.springcloud.entity.AccountDownloadVO;
import com.hust.springcloud.entity.AccountDto;
import com.hust.springcloud.entity.AccountVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AccountMapper extends BaseMapper<AccountDto> {
    public int saveBatch(List<AccountDto> list);
    public AccountVO selectByAccountName(String accountName);
    public List<AccountDownloadVO> selectByCreateTimeInterval(@Param("minCreateTime") String minCreateTime, @Param("maxCreateTime") String maxCreateTime);
}

package com.hust.springcloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hust.springcloud.entity.AccountDownloadVO;
import com.hust.springcloud.entity.AccountDto;
import com.hust.springcloud.entity.AccountVO;
import com.hust.springcloud.mapper.AccountMapper;
import com.hust.springcloud.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountDto> implements AccountService {
    @Override
    public int saveBatch(List<AccountDto> list) {
       return baseMapper.saveBatch(list);
    }

    @Override
    public AccountVO selectByAccountName(String accountName) {
        return baseMapper.selectByAccountName(accountName);
    }

    @Override
    public List<AccountDownloadVO> selectByCreateTimeInterval(String minCreateTime, String maxCreateTime) {
        return baseMapper.selectByCreateTimeInterval(minCreateTime,maxCreateTime);
    }
}

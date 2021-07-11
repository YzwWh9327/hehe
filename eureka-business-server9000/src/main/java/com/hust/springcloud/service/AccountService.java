package com.hust.springcloud.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hust.springcloud.entity.AccountDownloadVO;
import com.hust.springcloud.entity.AccountDto;
import com.hust.springcloud.entity.AccountVO;

import java.util.List;

public interface AccountService extends IService<AccountDto> {
    public int saveBatch(List<AccountDto> list);
    public AccountVO selectByAccountName(String accountName);
    public List<AccountDownloadVO> selectByCreateTimeInterval(String minCreateTime, String maxCreateTime);
}

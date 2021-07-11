package com.hust.springcloud.controller;


import com.hust.springcloud.common.ResponseEnum;
import com.hust.springcloud.common.Result;
import com.hust.springcloud.entity.AccountDownloadVO;
import com.hust.springcloud.entity.AccountDto;
import com.hust.springcloud.entity.AccountVO;
import com.hust.springcloud.exception.Assert;
import com.hust.springcloud.service.AccountService;
import com.hust.springcloud.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/account")
public class AccountController {

    @Resource
    private FileService fileService;
    @Resource
    private AccountService accountService;

    @PostMapping(value = "/upload")
    public Result upload(@RequestPart(value = "file") MultipartFile file){
        Assert.notNull(file, ResponseEnum.FILE_UPLOAD_ERROR);
        List<AccountDto> lists = fileService.upload(file);
        int res = accountService.saveBatch(lists);
        Assert.isTrue(res!=0,ResponseEnum.ACCOUNT_HIT_DB_ERROR);
        return Result.ok().message("文件上传成功");
    }

    @GetMapping("/list")
    public Result list(@RequestParam("accountName")String accountName){
        AccountVO accountVO = accountService.selectByAccountName(accountName);
        Assert.notNull(accountVO,ResponseEnum.ACCOUNT_INFO_NULL);
        return Result.ok().data("account",accountVO);
    }
    @GetMapping("/download")
    public void download(@RequestParam("min_create_date")String min_create_date,
                           @RequestParam("max_create_date")String max_create_date,
                           HttpServletResponse response){
        Assert.notEmpty(min_create_date,ResponseEnum.MIN_CREATE_DATE_NULL);
        Assert.notEmpty(max_create_date,ResponseEnum.MAX_CREATE_DATE_NULL);
        List<AccountDownloadVO> voList = accountService.selectByCreateTimeInterval(min_create_date, max_create_date);
        Assert.notNull(voList,ResponseEnum.ACCOUNT_INFO_NULL);

        fileService.download(response,voList);
    }
}

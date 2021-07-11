package com.hust.springcloud.controller;

import com.hust.springcloud.common.Result;
import com.hust.springcloud.service.AccountFeignService;
import com.hust.springcloud.utils.CsvImporExporttUtil;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@RestController
@Slf4j
@RequestMapping("consumer")
public class AccountFeignController {
    @Resource
    private AccountFeignService accountFeignService;

    @PostMapping(value = "upload")
    public Result upload(@RequestPart(value = "file") MultipartFile file){
        return accountFeignService.upload(file);
    }

    @GetMapping("list")
    public Result list(@RequestParam("accountName")String accountName){
        return accountFeignService.list(accountName);
    }
    @GetMapping("download")
    public void download(@RequestParam("min_create_date")String min_create_date,
                         @RequestParam("max_create_date")String max_create_date,
                         HttpServletResponse response){
        String fName = "AccountInfo_";
        try {
            CsvImporExporttUtil.responseSetProperties(fName,response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Response rs = accountFeignService.download(min_create_date, max_create_date, response);
        InputStream inputStream = null;
        ServletOutputStream outputStream =null;
        try {
            inputStream = rs.body().asInputStream();
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}

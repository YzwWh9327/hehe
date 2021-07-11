package com.hust.springcloud.service;

import com.hust.springcloud.entity.AccountDownloadVO;
import com.hust.springcloud.entity.AccountDto;
import com.hust.springcloud.entity.Gender;
import com.hust.springcloud.utils.CsvImporExporttUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    public List<AccountDto> upload(MultipartFile file){
        File uploadFile = CsvImporExporttUtil.uploadFile(file);
        List<List<String>> lists = CsvImporExporttUtil.readCSV(uploadFile.getPath(), 3);
        List<AccountDto> res = str2Obj(lists);
        return res;
    }

    private List<AccountDto> str2Obj(List<List<String>> lists){
        List<AccountDto> res = new ArrayList<>();
        for (List<String> list : lists) {
            AccountDto accountDto = new AccountDto();
            accountDto.setAccountName(list.get(0));
            accountDto.setName(list.get(1));
            accountDto.setGender(Gender.valueOf(list.get(2)));
            res.add(accountDto);
        }
        return res;
    }

    public void download(HttpServletResponse response, List<AccountDownloadVO>dataList){
        // 设置表头
        String titles = "account_name,name,gender,create_time,update_time";
        // 设置导出文件前缀
        String fName = "AccountInfo_";
        // 文件导出
        try {
            OutputStream os = response.getOutputStream();
            CsvImporExporttUtil.responseSetProperties(fName, response);
            CsvImporExporttUtil.doExport(dataList, titles, os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

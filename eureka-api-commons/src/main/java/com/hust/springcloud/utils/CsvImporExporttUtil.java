package com.hust.springcloud.utils;

import com.hust.springcloud.entity.AccountDownloadVO;
import com.hust.springcloud.exception.Assert;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class CsvImporExporttUtil {
    //上传文件的路径
    private final static URL PATH = Thread.currentThread().getContextClassLoader().getResource("");

    /**
     * @return File  一般文件类型
     * @Description 上传文件的文件类型
     * @Param multipartFile
     **/
    public static File uploadFile(MultipartFile multipartFile) {
        // 获 取上传 路径
        String path = PATH.getPath() + multipartFile.getOriginalFilename();
        try {
            // 通过将给定的路径名字符串转换为抽象路径名来创建新的 File实例
            File file = new File(path);
            // 此抽象路径名表示的文件或目录是否存在
            if (!file.getParentFile().exists()) {
                // 创建由此抽象路径名命名的目录，包括任何必需但不存在的父目录
                file.getParentFile().mkdirs();
            }
            // 转换为一般file 文件
            multipartFile.transferTo(file);

            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return List<List<String>>
     * @Description 读取CSV文件的内容（不含表头）
     * @Param filePath 文件存储路径，colNum 列数
     **/
    public static List<List<String>> readCSV(String filePath, int colNum) {
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(filePath);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            CSVParser parser = CSVFormat.DEFAULT.parse(bufferedReader);


            //  表内容集合，外层 List为行的集合，内层 List为字段集合
            List<List<String>> values = new ArrayList<>();
            int rowIndex = 0;
            Set<String>set=new HashSet<>();
            // 读取文件每行内容
            for (CSVRecord record : parser.getRecords()) {
                //  跳过表头
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                // 判断下角标是否越界
                if(colNum>record.size()){
                    // 返回空集合
                    return values;
                }
                //  每行的内容
                List<String> value = new ArrayList<>();
                for (int i = 0; i < colNum; i++) {
                    //校验每行字段是否合法
                    String str = record.get(i).trim();
                    if(i==0){
                        Assert.notValid(str==null||str.equals(""),String.format("文件第%d行数据为空",rowIndex));
                        int len = str.length();
                        Assert.notValid(len!=10|| !StringUtils.isNumeric(str), String.format("第%d行数据格式不正确",rowIndex));
                        Assert.notValid(set.contains(str),String.format("第%d行重复了",rowIndex));
                        set.add(str);
                    }else if(i==1){
                        Assert.notValid(str==null||str.equals(""),String.format("文件第%d行数据为空",rowIndex));
                        int len = str.length();
                        Assert.notValid(len>20,String.format("第%d行数据格式不正确",rowIndex));
                    }else {
                        Assert.notValid(str==null||str.equals(""),String.format("文件第%d行数据为空",rowIndex));
                        Assert.notValid(!(str.equals("M")||str.equals("F")),String.format("第%d行数据格式不正确",rowIndex));
                    }
                    value.add(record.get(i));
                }
                values.add(value);
                rowIndex++;
            }
            return values;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * CSV文件列分隔符
     */
    private static final String CSV_COLUMN_SEPARATOR = ",";

    /**
     * CSV文件行分隔符
     */
    private static final String CSV_ROW_SEPARATOR = "\r\n";

    /**
     * @param dataList
     *            集合数据
     * @param titles
     *            表头部数据
     * @param os
     *            输出流
     */
    public static void doExport(List<AccountDownloadVO> dataList, String titles, OutputStream os)
            throws Exception {

        // 保证线程安全
        StringBuffer buf = new StringBuffer();

        String[] titleArr = null;
        titleArr = titles.split(",");

        // 组装表头
        for (int i = 0; i < titleArr.length; i++) {
            if(i==titleArr.length-1){
                buf.append(titleArr[i]);
            }else {
                buf.append(titleArr[i]).append(CSV_COLUMN_SEPARATOR);
            }
        }
        buf.append(CSV_ROW_SEPARATOR);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 组装数据
        for (AccountDownloadVO accountDownloadVO : dataList) {
            buf.append(accountDownloadVO.getAccountName()).append(CSV_COLUMN_SEPARATOR);
            buf.append(accountDownloadVO.getName()).append(CSV_COLUMN_SEPARATOR);
            buf.append(accountDownloadVO.getGender().getGender()).append(CSV_COLUMN_SEPARATOR);
            buf.append(sdf.format(accountDownloadVO.getCreateTime())).append(CSV_COLUMN_SEPARATOR);
            buf.append(sdf.format(accountDownloadVO.getUpdateTime()));
            buf.append(CSV_ROW_SEPARATOR);
        }
        // 写出响应
        os.write(buf.toString().getBytes("UTF-8"));
        os.flush();
    }

    /**
     * 设置Header
     *
     * @param fileName
     * @param response
     * @throws UnsupportedEncodingException
     */
    public static void responseSetProperties(String fileName, HttpServletResponse response)
            throws UnsupportedEncodingException {
        // 设置文件后缀
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fn = fileName + sdf.format(new Date()) + ".csv";
        // 读取字符编码
        String utf = "UTF-8";

        // 设置响应
        response.setContentType("application/ms-txt.numberformat:@");
        response.setCharacterEncoding(utf);
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=30");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fn, utf));
    }

}

package com.hust.springcloud.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum ResponseEnum {
    SUCCESS(0, "成功"),
    ERROR(-1, "服务器内部错误"),

    //-1xx 服务器错误
    BAD_SQL_GRAMMAR_ERROR(-101, "sql语法错误"),
    SERVLET_ERROR(-102, "servlet请求异常"), //-2xx 参数校验
    UPLOAD_ERROR(-103, "文件上传错误"),
    EXPORT_DATA_ERROR(104, "数据导出失败"),


    //-2xx 参数校验
    BORROW_AMOUNT_NULL_ERROR(-201, "借款额度不能为空"),
    MOBILE_NULL_ERROR(-202, "手机号码不能为空"),
    MOBILE_ERROR(-203, "手机号码不正确"),
    USER_NULL_ERROR(201,"用户名不能为空"),
    PASSWORD_NULL_ERROR(204, "密码不能为空"),
    CODE_NULL_ERROR(205, "验证码不能为空"),
    CODE_ERROR(206, "验证码错误"),
    MOBILE_EXIST_ERROR(207, "手机号已被注册"),
    LOGIN_MOBILE_ERROR(208, "用户不存在"),
    LOGIN_PASSWORD_ERROR(209, "密码错误"),
    LOGIN_LOKED_ERROR(210, "用户被锁定"),
    LOGIN_AUTH_ERROR(-211, "未登录"),
    USER_PROFILE_MODIFY_ERROR(-212,"profile修改失败"),

    FILE_UPLOAD_ERROR(-301,"文件上传失败"),
    ACCOUNT_HIT_DB_ERROR(-302,"账户数据落库失败"),
    ACCOUNT_NAME_NULL(-303,"账户名为空"),
    ACCOUNT_INFO_NULL(-304,"账户信息不存在"),
    MIN_CREATE_DATE_NULL(-305,"传入最小创建时间不能为空"),
    MAX_CREATE_DATE_NULL(-306,"传入最大创建时间不能为空"),
    ;

    // 响应状态码
    private Integer code;
    // 响应信息
    private String message;
}

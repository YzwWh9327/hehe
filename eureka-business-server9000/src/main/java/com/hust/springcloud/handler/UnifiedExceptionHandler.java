package com.hust.springcloud.handler;

import com.hust.springcloud.common.ResponseEnum;
import com.hust.springcloud.common.Result;
import com.hust.springcloud.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@Component
@RestControllerAdvice
public class UnifiedExceptionHandler {

    /**
     * 未定义异常
     */
    @ExceptionHandler(value = Exception.class) //当controller中抛出Exception，则捕获
    public Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error().message("未定义异常");
    }

    /**
     * 特定异常
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public Result handleBadSqlGrammarException(BadSqlGrammarException e){
        log.error(e.getMessage(), e);
        return Result.setResult(ResponseEnum.BAD_SQL_GRAMMAR_ERROR);
    }

    /**
     *
     * 特定异常
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e){
        log.error(e.getMessage(), e);
        return Result.setResult(ResponseEnum.FILE_SIZE_TOO_BIG);
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e){
        log.error(e.getMessage(), e);
        return Result.error().message(e.getMessage()).code(e.getCode());
    }

    /**
     * Controller上一层相关异常
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodArgumentNotValidException.class,
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    public Result handleServletException(Exception e) {
        log.error(e.getMessage(), e);
        //SERVLET_ERROR(-102, "servlet请求异常"),
        return Result.error().message(ResponseEnum.SERVLET_ERROR.getMessage()).code(ResponseEnum.SERVLET_ERROR.getCode());
    }
}

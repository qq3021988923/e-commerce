package com.yang.handler;

import com.yang.constant.MessageConstant;
import com.yang.exception.BaseException;
import com.yang.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j // 将异常按照格式发送给前端Result.error
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result exceptionHandler(BaseException e) {
        log.error("异常信息：｛｝",e.getMessage());
        System.out.println("我是全局异常处理");
        return Result.error(e.getMessage());
    }


    @ExceptionHandler // 处理数据库用户名重复的异常 返回友好异常
    public Result exceptionHandler(SQLIntegrityConstraintViolationException e) {

        String mes=e.getMessage();
        if(mes.contains("Duplicate entry")){
            String[] split = mes.split(" ");
            String username=split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }else{
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }

    }

}

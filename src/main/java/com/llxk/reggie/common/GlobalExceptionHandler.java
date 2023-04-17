package com.llxk.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * ClassName: GlobalExceptionHandler
 * Package: com.llxk.reggie.common
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc 全局异常处理器
 * @Date 2023/4/17 22:28
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        if(ex.getMessage().contains("Duplicate entry")){
            return R.error("用户名已存在");
        }



        return R.error("位置错误");
    }

}

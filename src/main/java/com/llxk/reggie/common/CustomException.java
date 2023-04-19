package com.llxk.reggie.common;

/**
 * ClassName: CustomException
 * Package: com.llxk.reggie.common
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc 自定义业务异常
 * @Date 2023/4/19 18:00
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }

}

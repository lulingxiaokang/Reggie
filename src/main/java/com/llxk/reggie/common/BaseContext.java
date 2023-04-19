package com.llxk.reggie.common;

/**
 * ClassName: BaseContext
 * Package: com.llxk.reggie.common
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 * @Date 2023/4/19 16:14
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }

}

package com.xiaokong.reggie.utils;

/**
 * ClassName BaseContext
 * Description 基于ThreadLocal,用于保存和传递当前登录用户的id
 * Author Mrk
 * Date 2022/8/22 11:01
 * Version 1.0
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}

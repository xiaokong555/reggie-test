package com.xiaokong.reggie.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName R
 * Description 通用返回结果
 * Author Mrk
 * Date 2022/8/21 9:50
 * Version 1.0
 */
@Data
public class R<T> implements Serializable {


    private Integer code;
    private String msg;
    private T data;
    private Map map = new HashMap<>(); // 动态数据

    public static <T> R<T> success(T object) {
        R<T> r = new R<>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public Integer getCode() {
        return code;
    }

}

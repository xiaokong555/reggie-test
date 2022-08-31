package com.xiaokong.reggie.handler;

import com.xiaokong.reggie.common.CustomException;
import com.xiaokong.reggie.common.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * ClassName GlobalExceptionHandler
 * Description 全局异常处理器
 * Author Mrk
 * Date 2022/8/21 14:48
 * Version 1.0
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class GlobalExceptionHandler {
    /*
     * @Description: 数据新增异常捕获(违反unique原则)
     **/
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> ExceptionHandler(SQLIntegrityConstraintViolationException ex) {
        boolean contains = ex.getMessage().contains("Duplicate entry");
        if (contains) {
            String name = ex.getMessage().split(" ")[2];
            return R.error(name + "已存在,请换一个吧");
        } else {
            return R.error("意外错误,请联系管理员");
        }
    }

    /*
     * Description: 删除菜品套餐种类时,对应数据有关联数据时删除抛出异常到该处处理
     **/
    @ExceptionHandler(CustomException.class)
    public R<String> CustomExceptionHandler(CustomException ex) {
        return R.error(ex.getMessage());
    }
}

package com.xiaokong.reggie.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xiaokong.reggie.utils.BaseContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * ClassName MetaObjectHandler
 * Description Mybatis-plus自动填充设置类
 * Author Mrk
 * Date 2022/8/22 10:06
 * Version 1.0
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /*
     * @Description: 执行插入与更新时字段的自动填充工作
     * @Param metaObject: 客户端传来的原始Employee对象
     * @return: void
     **/
    @Override
    public void insertFill(MetaObject metaObject) {
        Long id = BaseContext.getCurrentId();
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", id);
        metaObject.setValue("updateUser", id);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long id = BaseContext.getCurrentId();
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", id);
    }
}

package com.xiaokong.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaokong.reggie.pojo.Category;

public interface CategoryService extends IService<Category> {

    void remove(Long id);

}

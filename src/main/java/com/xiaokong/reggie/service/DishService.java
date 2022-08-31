package com.xiaokong.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaokong.reggie.dto.DishDto;
import com.xiaokong.reggie.pojo.Dish;

/**
 * InterfaceName EmployeeService
 * Description
 * Implements
 * Date 2022/8/21 9:57
 * Version 1.0
 */
public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);
}

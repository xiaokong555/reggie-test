package com.xiaokong.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaokong.reggie.dto.SetmealDto;
import com.xiaokong.reggie.pojo.Setmeal;

/**
 * InterfaceName EmployeeService
 * Description
 * Implements
 * Date 2022/8/21 9:57
 * Version 1.0
 */
public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);
}

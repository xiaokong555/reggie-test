package com.xiaokong.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaokong.reggie.dto.SetmealDto;
import com.xiaokong.reggie.mapper.SetmealMapper;
import com.xiaokong.reggie.pojo.Setmeal;
import com.xiaokong.reggie.pojo.SetmealDish;
import com.xiaokong.reggie.service.SetmealDishService;
import com.xiaokong.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

/**
 * ClassName CategoryServiceImpl
 * Description LookMe
 * Author Mrk
 * Date 2022/8/22 11:41
 * Version 1.0
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        Long setmealId = setmealDto.getId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
            setmealDishService.save(setmealDish);
        });
    }

    @Autowired
    private SetmealDishService setmealDishService;
}

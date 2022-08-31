package com.xiaokong.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaokong.reggie.common.CustomException;
import com.xiaokong.reggie.mapper.CategoryMapper;
import com.xiaokong.reggie.pojo.Category;
import com.xiaokong.reggie.pojo.Dish;
import com.xiaokong.reggie.pojo.Setmeal;
import com.xiaokong.reggie.service.CategoryService;
import com.xiaokong.reggie.service.DishService;
import com.xiaokong.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName CategoryServiceImpl
 * Description LookMe
 * Author Mrk
 * Date 2022/8/22 11:41
 * Version 1.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Dish::getCategoryId, id);
        long count = dishService.count(lqw);
        if (count > 0) {
            throw new CustomException("当前分类下关联了菜品,无法删除!");
        }

        LambdaQueryWrapper<Setmeal> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Setmeal::getCategoryId, id);
        long count2 = setmealService.count(lqw2);
        if (count2 > 0) {
            throw new CustomException("当前分类下关联了套餐,无法删除!");
        }

        super.removeById(id);
    }
}

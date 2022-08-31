package com.xiaokong.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaokong.reggie.common.R;
import com.xiaokong.reggie.dto.DishDto;
import com.xiaokong.reggie.pojo.Category;
import com.xiaokong.reggie.pojo.Dish;
import com.xiaokong.reggie.pojo.DishFlavor;
import com.xiaokong.reggie.service.CategoryService;
import com.xiaokong.reggie.service.DishFlavorService;
import com.xiaokong.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName SetmealContorller
 * Description LookMe
 * Author Mrk
 * Date 2022/8/22 14:51
 * Version 1.0
 */
@RestController
@RequestMapping("/dish")
public class DishContorller {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page<DishDto>> getByPage(int page, int pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(name), Dish::getName, name);
        lqw.orderByAsc(Dish::getPrice);
        dishService.page(pageInfo, lqw);

        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<DishDto> dishDtos = pageInfo.getRecords().stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String s = category.getName();
                dishDto.setCategoryName(s);
            } else {
                dishDto.setCategoryName("不存在");
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(dishDtos);

        return R.success(dishDtoPage);
    }

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("添加成功");
    }

    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatusById(@PathVariable Integer status, Long[] ids) {
        for (Long id : ids) {
            Dish dish = new Dish();
            dish.setId(id);
            dish.setStatus(status);
            dishService.updateById(dish);
        }
        return R.success("修改成功");
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

    @DeleteMapping
    public R<String> deleteByIds(Long[] ids) {
        for (Long id : ids) {
            LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
            lqw.eq(id != null, DishFlavor::getDishId, id);
            dishFlavorService.remove(lqw);
            dishService.removeById(id);
        }
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<List<Dish>> getByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(categoryId != null, Dish::getCategoryId, categoryId).eq(Dish::getStatus, 1);
        List<Dish> dishes = dishService.list(lqw);
        return R.success(dishes);
    }
}

package com.xiaokong.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.xiaokong.reggie.common.R;
import com.xiaokong.reggie.dto.SetmealDto;
import com.xiaokong.reggie.pojo.Setmeal;
import com.xiaokong.reggie.service.CategoryService;
import com.xiaokong.reggie.service.SetmealService;
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
@RequestMapping("/setmeal")
public class SetmealContorller {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page<SetmealDto>> getByPage(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> pageDto = new Page<>();
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        lqw.orderByAsc(Setmeal::getPrice);
        setmealService.page(pageInfo, lqw);
        BeanUtils.copyProperties(pageInfo, pageDto, "records");

        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> dtoList = records.stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            String s = categoryService.getById(categoryId).getName();
            setmealDto.setCategoryName(s);
            return setmealDto;
        }).collect(Collectors.toList());
        pageDto.setRecords(dtoList);
        return R.success(pageDto);
    }

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithDish(setmealDto);
        return R.success("保存成功");
    }
}

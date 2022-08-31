package com.xiaokong.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaokong.reggie.common.R;
import com.xiaokong.reggie.pojo.Category;
import com.xiaokong.reggie.pojo.Employee;
import com.xiaokong.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName CategoryController
 * Description Category表的业务层
 * Author Mrk
 * Date 2022/8/22 11:31
 * Version 1.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /*
     * Description: 添加功能
     **/
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page<Category>> getByPage(int page, int pageSize, String name) {
        // 构造分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(name), Category::getName, name);
        // 添加排序条件
        lqw.orderByAsc(Category::getType);
        lqw.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, lqw);
        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> deleteById(Long ids) {
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    @PutMapping
    public R<String> updateById(HttpServletRequest request, @RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("更改成功");
    }

    @GetMapping("/list")
    public R<List<Category>> getByType(Category category) {
        if (category != null) {
            LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
            lqw.eq(category.getType() != null, Category::getType, category.getType());
            List<Category> list = categoryService.list(lqw);
            return R.success(list);
        } else {
            return R.error("查询失败");
        }
    }
}

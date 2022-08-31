package com.xiaokong.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaokong.reggie.common.R;
import com.xiaokong.reggie.pojo.Employee;
import com.xiaokong.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * ClassName Login
 * Description LookMe
 * Author Mrk
 * Date 2022/8/21 9:45
 * Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /*
     * @Description: 员工登录
     * @Param request:
     * @Param employee:
     * @return: com.xiaokong.reggie.common.R<com.xiaokong.reggie.pojo.Employee>
     **/
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 将密码进行MD5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 查询数据库(name)
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        // 可以在这里控制查询结果的集
        // queryWrapper.select(Employee::getId, Employee::getUsername, Employee::getPassword, Employee::getStatus);
        Employee one = employeeService.getOne(queryWrapper);
        if (one == null) {
            return R.error("登陆失败,请检查你的用户名与密码");
        }

        // 密码比对
        if (!one.getPassword().equals(password)) {
            return R.error("登陆失败,请检查你的用户名与密码");
        }

        // 检查账户活跃状态
        if (one.getStatus() == 0) {
            return R.error("登陆失败,该账户已被冷却");
        }
        request.getSession().setAttribute("employee", one.getId());
        return R.success(one);
    }

    /*
     * @Description: 员工退出
     * @Param request:
     * @return: com.xiaokong.reggie.common.R<java.lang.String>
     **/
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        // 清理session
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /*
     * @Description: 新增员工
     * @Param employee:
     * @return: com.xiaokong.reggie.common.R<java.lang.String>
     **/
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        // 设置初始默认密码为123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<Page<Employee>> getByPage(int page, int pageSize, String name) {
        // 构造分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        // 添加排序条件
        lqw.orderByAsc(Employee::getId);

        employeeService.page(pageInfo, lqw);
        return R.success(pageInfo);
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        } else {
            return R.error("未查询到该用户");
        }
    }

    /*
     * @Description: 修改员工数据
     * 由于员工ID为19位长度的long数据类型,而JS默认最长只能解析16位精度的数字,所以需要配置消息转换器自动将
     * 返回json中的id类型值由long改为String
     * 配置好消息转换器后,添加到容器中默认的转换器列表中,且使索引为0,确保能够被优先调用到
     * @Param employee
     * @return: com.xiaokong.reggie.common.R<java.lang.String>
     **/
    @PutMapping
    public R<String> updateById(HttpServletRequest request, @RequestBody Employee employee) {
        if (employee.getId() == 1L && employee.getStatus() == 0) {
            return R.error("您没有这个权限!");
        }
        employeeService.updateById(employee);
        return R.success("账号状态更改成功!");
    }
}

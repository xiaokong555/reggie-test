package com.xiaokong.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaokong.reggie.mapper.EmployeeMapper;
import com.xiaokong.reggie.pojo.Employee;
import com.xiaokong.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * ClassName EmployeeServiceImpl
 * Description LookMe
 * Author Mrk
 * Date 2022/8/21 9:58
 * Version 1.0
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {
}

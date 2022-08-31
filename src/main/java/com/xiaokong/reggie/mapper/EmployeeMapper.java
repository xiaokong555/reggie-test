package com.xiaokong.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaokong.reggie.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName EmployeeMapper
 * Description LookMe
 * Author Mrk
 * Date 2022/8/21 10:11
 * Version 1.0
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}

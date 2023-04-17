package com.llxk.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.llxk.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: EmployeeMapper
 * Package: com.llxk.reggie.mapper
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/17 15:55
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}

package com.llxk.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llxk.reggie.common.R;
import com.llxk.reggie.entity.Employee;
import com.llxk.reggie.mapper.EmployeeMapper;
import com.llxk.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * ClassName: EmployeeServiceImpl
 * Package: com.llxk.reggie.service.impl
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/17 15:59
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Override
    public R<Employee> login(Employee employee){
    //1.将页面提交的密码password进行md5加密处理
    String password = employee.getPassword();
    password = DigestUtils.md5DigestAsHex(password.getBytes());

    //2.根据页面提交的用户名username查询数据库
    LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
    Employee emp = getOne(queryWrapper);

    //3.没有查询到则返回登陆失败
        if (emp == null) {
        return R.error("登陆失败(用户名不存在)");
    }

    //4.密码比对，如果不一致也返回登陆失败
        if(!emp.getPassword().equals(password)){
        return R.error("登陆失败(密码错误)");
    }

    //5.查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(emp.getStatus() == 0){
        return R.error("账号已禁用");
    }

    //6.登陆成功，(将员工id存入session并返回登陆成功结果)
        return R.success(emp);
    }

    @Override
    public R<String> logout() {
        return R.success("退出成功");
    }


}

package com.llxk.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.llxk.reggie.common.R;
import com.llxk.reggie.entity.Employee;

/**
 * ClassName: EmployeeService
 * Package: com.llxk.reggie.service
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/17 15:57
 */
public interface EmployeeService extends IService<Employee> {
    /**
     * 员工登陆
     * @param employee 页面post数据封装的employee对象（username和password）
     * @return 通用结果R
     */
    R<Employee> login(Employee employee);

    /**
     * 员工登出
     * @return 退出成功信息
     */
    R<String> logout();
}

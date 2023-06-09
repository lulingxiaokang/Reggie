package com.llxk.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llxk.reggie.common.R;
import com.llxk.reggie.entity.Employee;
import com.llxk.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: EmployeeController
 * Package: com.llxk.reggie.controller
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/17 16:28
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    /**
     * 员工登陆
     * @param request request请求
     * @param employee 页面post数据封装的employee对象
     * @return 通用结果R
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //调用employeeService的login方法返回R对象
        R<Employee> r = employeeService.login(employee);
        //判断是否能登陆成功
        if(r.getCode() == 1){
            request.getSession().setAttribute("employee", r.getData().getId());
        }
        return r;
    }

    /**
     * 员工退出
     * @param request request
     * @return 退出成功信息
     */
    @RequestMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return employeeService.logout();

    }


    /**
     * 新增员工
     * @param request request
     * @param employee 页面post数据封装的employee对象
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        log.info("新增员工，员工信息：{}", employee.toString());
        //设置初始密码123456，并进行，md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        /*employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //获取当前用户id
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);*/

        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    /**
     * 员工信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        //log.info("page={},pageSize={},name={}", page, pageSize, name);

        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getUsername, name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据id修改员工信息
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){

        /*Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateUser(empId);
        employee.setUpdateTime(LocalDateTime.now());*/
        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }


}

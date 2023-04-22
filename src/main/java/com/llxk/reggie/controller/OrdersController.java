package com.llxk.reggie.controller;

import com.llxk.reggie.common.R;
import com.llxk.reggie.entity.Orders;
import com.llxk.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * ClassName: OrdersController
 * Package: com.llxk.reggie.controller
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/23 1:57
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {

    @Resource
    private OrdersService ordersService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){

        ordersService.submit(orders);

        return R.success("下单成功");
    }
}

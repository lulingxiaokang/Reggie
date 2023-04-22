package com.llxk.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.llxk.reggie.entity.Orders;

/**
 * ClassName: OrdersService
 * Package: com.llxk.reggie.service
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/23 1:55
 */
public interface OrdersService extends IService<Orders> {
    /**
     * 用户下单
     * @param orders
     */
    void submit(Orders orders);

}

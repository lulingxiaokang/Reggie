package com.llxk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llxk.reggie.entity.OrderDetail;
import com.llxk.reggie.mapper.OrderDetailMapper;
import com.llxk.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * ClassName: OrderDetailServiceImpl
 * Package: com.llxk.reggie.service.impl
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/23 1:54
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}

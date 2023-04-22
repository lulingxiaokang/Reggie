package com.llxk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llxk.reggie.entity.ShoppingCart;
import com.llxk.reggie.mapper.ShoppingCartMapper;
import com.llxk.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * ClassName: ShoppingCartServiceImpl
 * Package: com.llxk.reggie.service.impl
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/23 0:39
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}

package com.llxk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llxk.reggie.entity.Dish;
import com.llxk.reggie.mapper.DishMapper;
import com.llxk.reggie.service.DishService;
import org.springframework.stereotype.Service;

/**
 * ClassName: DishServiceImpl
 * Package: com.llxk.reggie.service.impl
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/19 17:42
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService{
}

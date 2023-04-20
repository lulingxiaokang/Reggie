package com.llxk.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.llxk.reggie.dto.DishDto;
import com.llxk.reggie.entity.Dish;

/**
 * ClassName: DishService
 * Package: com.llxk.reggie.service
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/19 17:41
 */
public interface DishService extends IService<Dish> {

    /**
     * 新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish, dish_flavor
     * @param dishDto
     */
    void saveWithFlavor(DishDto dishDto);

    /**
     * 根据id查询菜品信息和口味信息
     * @param id
     * @return
     */
    DishDto getByIdWithFlavor(Long id);

    /**
     * 更新菜品信息，同时更新口味信息
     * @param dishDto
     */
    void updateWithFlavor(DishDto dishDto);

    /**
     * 根据id删除菜品信息，并删除对应口味信息
     * @param id
     */
    void remove(Long id);
}

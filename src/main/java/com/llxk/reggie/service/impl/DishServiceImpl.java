package com.llxk.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llxk.reggie.common.CustomException;
import com.llxk.reggie.dto.DishDto;
import com.llxk.reggie.entity.Dish;
import com.llxk.reggie.entity.DishFlavor;
import com.llxk.reggie.entity.SetmealDish;
import com.llxk.reggie.mapper.DishMapper;
import com.llxk.reggie.service.DishFlavorService;
import com.llxk.reggie.service.DishService;
import com.llxk.reggie.service.SetmealDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private DishFlavorService dishFlavorService;

    @Resource
    private SetmealDishService setmealDishService;
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        //菜品id
        Long dishId = dishDto.getId();

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(dishDto.getFlavors());

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        //查询菜品口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, id);
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新菜品信息
        this.updateById(dishDto);

        //清理当前菜品口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        //添加口味信息
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);


    }

    @Override
    @Transactional
    public void removeWithFlavor(List<Long> ids) {
        /*
        //条件构造器
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();

        //查询条件
        setmealDishLambdaQueryWrapper.eq(SetmealDish::getDishId, id);
        //查询是否有套菜关联了当前菜品，如已关联，抛出一个业务异常
        int count = setmealDishService.count(setmealDishLambdaQueryWrapper);

        if(count > 0){
            //已被套餐关联，抛出一个业务异常
            throw new CustomException("当前菜品已被其他套餐关联，不能删除");
        }*/


        //查询菜品状态，确定是否可以删除（禁售菜品需不关联套餐，因此状态为禁用才可删除菜品）
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids)
                .eq(Dish::getStatus, 1);
        int count = this.count(queryWrapper);

        //如果不能删除，抛出一个业务异常
        if(count > 0){
            throw new CustomException("菜品正在售卖中，不能删除");
        }

        //删除菜品信息
        this.removeByIds(ids);

        //条件构造器
        LambdaQueryWrapper<DishFlavor> flavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //删除条件
        flavorLambdaQueryWrapper.in(DishFlavor::getDishId, ids);
        //删除口味信息
        dishFlavorService.remove(flavorLambdaQueryWrapper);

    }

    @Override
    @Transactional
    public void updateStatus(int status, List<Long> ids) {
        //禁售前需查看菜品是否关联了套餐
        if(status == 0){
            LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SetmealDish::getDishId, ids);
            int count = setmealDishService.count(queryWrapper);
            //如果有菜品被套餐关联，则不能禁售，抛出业务异常
            if(count > 0){
                throw new CustomException("菜品已被其他套餐关联，不能禁售");
            }
        }
        //没有被套餐关联或启售可执行
        for (Long id : ids) {
            Dish dish = new Dish();
            dish.setId(id);
            dish.setStatus(status);
            this.updateById(dish);
        }
    }


}

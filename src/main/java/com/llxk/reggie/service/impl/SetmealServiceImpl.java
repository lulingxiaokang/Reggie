package com.llxk.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llxk.reggie.common.CustomException;
import com.llxk.reggie.dto.SetmealDto;
import com.llxk.reggie.entity.Setmeal;
import com.llxk.reggie.entity.SetmealDish;
import com.llxk.reggie.mapper.SetmealMapper;
import com.llxk.reggie.service.SetmealDishService;
import com.llxk.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: SetmealServiceImpl
 * Package: com.llxk.reggie.service.impl
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/19 17:44
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Resource
    private SetmealDishService setmealDishService;

    @Resource
    private SetmealMapper setmealMapper;


    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐基本信息
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品的关联关系
        setmealDishService.saveBatch(setmealDishes);


    }

    @Override
    public Page<SetmealDto> pageSetmealDto(Page<SetmealDto> dtoPage, String name) {
        return setmealMapper.pageSetmealDto(dtoPage, name);
    }

    @Transactional
    @Override
    public void removeWithDish(List<Long> ids) {
        //查询套菜状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids)
                .eq(Setmeal::getStatus, 1);
        int count = this.count(queryWrapper);

        //如果不能删除，抛出一个业务异常
        if(count > 0){
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        //如果可以删除，先删除套餐表中的数据
        this.removeByIds(ids);

        //删除套餐关系表中的数据
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);

        setmealDishService.remove(setmealDishLambdaQueryWrapper);
    }

    @Override
    public void updateStatus(int status, List<Long> ids) {
        for (Long id : ids) {
            Setmeal setMeal = new Setmeal();
            setMeal.setId(id);
            setMeal.setStatus(status);
            this.updateById(setMeal);
        }
    }

    @Override
    public SetmealDto getSetmealDto(Long id) {
        SetmealDto setmealDto = setmealMapper.getSetmealDto(id);

        //查询关联的菜品信息
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, id);

        setmealDto.setSetmealDishes(setmealDishService.list(queryWrapper));



        return setmealDto;
    }

    @Override
    @Transactional
    public void updateWithDish(SetmealDto setmealDto) {
        //更新套餐信息
        this.updateById(setmealDto);

        //清理当前套餐关联菜品信息
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());

        setmealDishService.remove(queryWrapper);

        //添加菜品关联信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品的关联关系
        setmealDishService.saveBatch(setmealDishes);

    }


}

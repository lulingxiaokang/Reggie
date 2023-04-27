package com.llxk.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llxk.reggie.common.R;
import com.llxk.reggie.dto.SetmealDto;
import com.llxk.reggie.entity.Setmeal;
import com.llxk.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: SetmealController
 * Package: com.llxk.reggie.controller
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc 套餐管理
 * @Date 2023/4/20 21:02
 */

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Resource
    private SetmealService setmealService;




    @PostMapping
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }


    /**
     * 查询套餐分页信息
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        /*//分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);

        //条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //查询条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        //排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, queryWrapper);*/

        Page<SetmealDto> dtoPage = new Page<>(page, pageSize);
        dtoPage = setmealService.pageSetmealDto(dtoPage, name);
        return R.success(dtoPage);

    }

    /**
     * 删除套餐，同时删除套餐和菜品的关联数据
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("套餐数据删除成功");
    }

    /**
     * (批量)停售/起售套餐
     * @return
     */
    @PostMapping("/status/{status}")
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R<String> status(@PathVariable int status, @RequestParam List<Long> ids){
        setmealService.updateStatus(status, ids);
        return R.success("操作成功");
    }

    /**
     * 查询套现DTO信息，用于回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getSetmealDto(@PathVariable Long id){
        return R.success(setmealService.getSetmealDto(id));
    }

    /**
     * 修改套餐信息
     * @param setmealDto
     * @return
     */
    @PutMapping
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateWithDish(setmealDto);
        return R.success("修改套餐信息成功");
    }


    /**
     * 展示某类型的所有套餐信息
     * @param setmeal
     * @return
     */
    /*@GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);
    }*/
    @GetMapping("/list")
    @Cacheable(value = "setmealCache", key = "#setmeal.categoryId + '_' + #setmeal.status")
    public R<List<SetmealDto>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);

        List<SetmealDto> setmealDtos = new ArrayList<>();
        for (Setmeal setmeal1 : list) {
            setmealDtos.add(setmealService.getSetmealDto(setmeal1.getId()));
        }
        return R.success(setmealDtos);
    }

}

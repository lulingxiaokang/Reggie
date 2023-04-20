package com.llxk.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.llxk.reggie.dto.SetmealDto;
import com.llxk.reggie.entity.Setmeal;

import java.util.List;

/**
 * ClassName: SetmealService
 * Package: com.llxk.reggie.service
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/19 17:41
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 查询套餐分页信息
     * @param dtoPage
     * @param name
     * @return
     */
    Page<SetmealDto> pageSetmealDto(Page<SetmealDto> dtoPage, String name);

    /**
     * 删除套餐，同时删除套餐和菜品的关联数据
     * @param ids
     */
    void removeWithDish(List<Long> ids);

    /**
     * (批量)停售/起售套餐
     * @param ids
     */
    void updateStatus(int status, List<Long> ids);

    /**
     * 查询套现DTO信息，用于回显
     * @param id
     * @return
     */
    SetmealDto getSetmealDto(Long id);

    /**
     * 更新套餐即关联菜品信息
     * @param setmealDto
     */
    void updateWithDish(SetmealDto setmealDto);
}

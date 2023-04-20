package com.llxk.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llxk.reggie.dto.SetmealDto;
import com.llxk.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ClassName: SetmealMapper
 * Package: com.llxk.reggie.mapper
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/19 17:39
 */
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    /**
     * 查询套餐分页信息
     * @param dtoPage
     * @param name
     * @return
     */
    Page<SetmealDto> pageSetmealDto(@Param("dtoPage") Page<SetmealDto> dtoPage, @Param("name") String name);

    /**
     * 查询套现DTO信息，用于回显
     * @param id
     * @return
     */
    SetmealDto getSetmealDto(@Param("id") Long id);
}

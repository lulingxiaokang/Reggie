package com.llxk.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.llxk.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: DishMapper
 * Package: com.llxk.reggie.mapper
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/19 17:39
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}

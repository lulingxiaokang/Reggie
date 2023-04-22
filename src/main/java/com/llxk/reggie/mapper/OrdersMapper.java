package com.llxk.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.llxk.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: OrdersMapper
 * Package: com.llxk.reggie.mapper
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/23 1:55
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}

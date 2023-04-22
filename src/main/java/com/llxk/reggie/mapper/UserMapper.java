package com.llxk.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.llxk.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: UserMapper
 * Package: com.llxk.reggie.mapper
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/21 15:30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}

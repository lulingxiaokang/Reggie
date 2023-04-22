package com.llxk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llxk.reggie.entity.User;
import com.llxk.reggie.mapper.UserMapper;
import com.llxk.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserServiceImpl
 * Package: com.llxk.reggie.service.impl
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/21 15:31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}

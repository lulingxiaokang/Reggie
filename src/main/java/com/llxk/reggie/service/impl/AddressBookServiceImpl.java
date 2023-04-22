package com.llxk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.llxk.reggie.entity.AddressBook;
import com.llxk.reggie.mapper.AddressBookMapper;
import com.llxk.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * ClassName: AddressBookServiceImpl
 * Package: com.llxk.reggie.service.impl
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/21 17:26
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}

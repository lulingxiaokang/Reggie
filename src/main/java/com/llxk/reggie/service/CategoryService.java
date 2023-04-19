package com.llxk.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.llxk.reggie.entity.Category;

/**
 * ClassName: CategoryService
 * Package: com.llxk.reggie.service
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/19 16:53
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根据id删除分类，删除前需要进行判断
     * @param id
     */
    void remove(Long id);
}

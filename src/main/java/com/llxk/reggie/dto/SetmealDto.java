package com.llxk.reggie.dto;

import com.llxk.reggie.entity.Setmeal;
import com.llxk.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}

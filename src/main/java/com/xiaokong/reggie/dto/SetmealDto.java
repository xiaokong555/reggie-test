package com.xiaokong.reggie.dto;

import com.xiaokong.reggie.pojo.Setmeal;
import com.xiaokong.reggie.pojo.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}

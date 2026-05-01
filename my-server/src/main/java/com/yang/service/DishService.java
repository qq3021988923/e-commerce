package com.yang.service;

import com.yang.dto.DishPageQueryDTO;
import com.yang.entity.Dish;
import com.yang.result.PageResult;
import com.yang.vo.DishVO;

import java.util.List;

public interface DishService {

    PageResult pageQuery(DishPageQueryDTO dto);

    DishVO getByIdWithFlavor(Long id);

    public List<Dish> list(Long categoryId);

    public void saveWithFlavor(DishVO dto);

    void update(DishVO v);

    void deleteBetch(List<Long> ids);

    public List<DishVO> listWithFlavor(Dish d);

    void getSetMealStatusbyDishId(Long id,Integer status);
}

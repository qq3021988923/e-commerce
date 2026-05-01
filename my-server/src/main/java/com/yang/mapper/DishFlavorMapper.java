package com.yang.mapper;

import com.yang.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {


    List<DishFlavor> getByDishId(Long dishId);

    void insertBatch(List<DishFlavor> flavors);

    void deleteByDishId(Long dishId);
}

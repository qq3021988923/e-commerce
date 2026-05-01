package com.yang.mapper;

import com.github.pagehelper.Page;
import com.yang.annotation.AutoFill;
import com.yang.dto.DishPageQueryDTO;
import com.yang.entity.Dish;
import com.yang.enumeration.OperationType;
import com.yang.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {


    Integer countByCategoryId(Long categoryId);

    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    Dish getById(Long id);

    void deleteById(Long id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    List<Dish> list(Dish dish);

    List<Dish> getBySetmealId(Long setmealId);

    Integer countByMap(Map map);

    Integer countOnSaleSetmealByDishId(@Param("dishId") Long dishId);

}

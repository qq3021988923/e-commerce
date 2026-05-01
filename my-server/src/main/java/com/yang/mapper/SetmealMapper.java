package com.yang.mapper;

import com.github.pagehelper.Page;
import com.yang.annotation.AutoFill;
import com.yang.dto.SetmealPageQueryDTO;
import com.yang.entity.Setmeal;
import com.yang.enumeration.OperationType;
import com.yang.vo.DishItemVO;
import com.yang.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealMapper {

    Integer countByCategoryId(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    Setmeal getById(Long id);

    void deleteById(Long setmealId);

    List<SetmealVO> getByIdWithDish(Long id);

     List<Setmeal> list(Setmeal setmeal);

    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    Integer countByMap(Map map);

}

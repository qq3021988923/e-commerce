package com.yang.service;

import com.yang.dto.SetmealDTO;
import com.yang.dto.SetmealPageQueryDTO;
import com.yang.entity.Setmeal;
import com.yang.result.PageResult;
import com.yang.vo.DishItemVO;
import com.yang.vo.SetmealOverViewVO;
import com.yang.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

   PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

   List<SetmealVO> getByIdWithDish(Long id);

   void startOrStop(Setmeal s);

   void saveWithDish(SetmealDTO dto);

   void update(SetmealDTO setmealDTO);

   public void deleteBatch(List<Long> ids);

   List<Setmeal> list(Setmeal setmeal);

   List<DishItemVO> getDishItemBySetmealId(Long setmealId);

}

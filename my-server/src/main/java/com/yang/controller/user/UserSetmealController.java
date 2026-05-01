package com.yang.controller.user;


import com.yang.constant.StatusConstant;
import com.yang.entity.Setmeal;
import com.yang.result.Result;
import com.yang.service.SetmealService;
import com.yang.vo.DishItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/setmeal")
@Tag(name = "套餐浏览接口")
public class UserSetmealController {

    @Autowired
    private SetmealService setmealService;

    @GetMapping("/list")
    @Cacheable(cacheNames = "setmealCache",key = "#categoryId") // 缓存到redis
    @Operation(summary = "根据分类id查询套餐")
    public Result<List<Setmeal>> list(Long categoryId) {
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);
        List<Setmeal> list = setmealService.list(setmeal);
        return Result.success(list);
    }

    // 根据套餐id查询包含的菜品列表
    @GetMapping("/dish/{id}")
    @Operation(summary = "根据套餐id查询包含的菜品列表")
    public Result<List<DishItemVO>> dishList(@PathVariable Long id){

        List<DishItemVO> list = setmealService.getDishItemBySetmealId(id);
        return Result.success(list);
    }

}

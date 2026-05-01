package com.yang.controller.user;


import com.yang.entity.Dish;
import com.yang.result.Result;
import com.yang.service.CategoryService;
import com.yang.service.DishService;
import com.yang.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/dish")
@Slf4j
@Tag(name = "菜品浏览接口")
public class UserDishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/list")
    @Operation(summary = "根据分类id查询菜品列表+口味")
    public Result<List<DishVO>> list(Long categoryId){

        String key = "dish_" + categoryId;
        List<DishVO> dvo = (List<DishVO>)redisTemplate.opsForValue().get(key);

        if(dvo !=null && !dvo.isEmpty()){
            return Result.success(dvo);
        }

        Dish dish=new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(1);

        dvo = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key,dvo);

        return Result.success(dvo);
    }

}

package com.yang.controller.admin;

import com.yang.dto.DishPageQueryDTO;
import com.yang.entity.Dish;
import com.yang.mapper.DishFlavorMapper;
import com.yang.mapper.DishMapper;
import com.yang.result.PageResult;
import com.yang.result.Result;
import com.yang.service.DishService;
import com.yang.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Tag(name = "菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired // 用户端查询菜品时 ：先查缓存，缓存没有再查数据库 当前是管理端
    private RedisTemplate redisTemplate;



    @PostMapping("/page")
    @Operation(summary = "菜品分页查询")
    public PageResult pageQuery(DishPageQueryDTO dto){
        log.info("菜品分页查询：{}", dto);
        return dishService.pageQuery(dto);
    }

    @Operation(summary = "根据id查询菜品+口味")
    @GetMapping("/{id}") // 菜品+口味
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("根据id查询菜品+口味：{}", id);
        DishVO byIdWithFlavor = dishService.getByIdWithFlavor(id);

//        String cacheKey = "dish_detail_" + id; // 关键：修改Key前缀，区分单个对象和列表
//        DishVO dishVO = (DishVO) redisTemplate.opsForValue().get(cacheKey);
//        if (dishVO != null) {
//            System.out.println("我是缓存"+dishVO);
//            return Result.success(dishVO); // 缓存命中，返回读取到的单个对象
//        }
//        dishVO = dishService.getByIdWithFlavor(id);
//       // 3. 存入单个对象到Redis（类型匹配）
//        redisTemplate.opsForValue().set(cacheKey, dishVO, 30, TimeUnit.MINUTES);

        return Result.success(byIdWithFlavor);
    }


    //　根据分类ｉｄ查询菜品大类
    @GetMapping("/list/{categoryId}")
    @Operation(summary = "根据分类id查询菜品 X")
    public Result<List<Dish>> list2(@PathVariable Long categoryId){
        log.info("根据分类id查询菜品：{}", categoryId);
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    // http://localhost:1234/admin/dish/status
    @GetMapping("status/{status}")
    @Operation(summary = "根据菜品停启状态")
    public Result<String> status(Long id,@PathVariable Integer status){
    dishService.getSetMealStatusbyDishId(id,status);
        return Result.success();
    }

    @GetMapping("/list")
    @Operation(summary = "查询所有菜品 X")
    public Result<List<Dish>> listAll(){
        log.info("查询所有菜品");
        List<Dish> list = dishService.list(null);
        return Result.success(list);
    }

    @PostMapping("/save") // 添加菜品 带口味版
    @Operation(summary = "新增菜品")
    public Result<String> save(@RequestBody DishVO dishVO){
        log.info("查询所有菜品:{}",dishVO);
        dishService.saveWithFlavor(dishVO);

        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "修改菜品")
    public Result<String> update(@RequestBody DishVO dishVO){
        log.info("修改菜品:{}",dishVO);
        dishService.update(dishVO);

        cleanCache("dish_*"); // 以dish_开头的
        return Result.success();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "批量删除")
    public Result<String> deleteBetch(@RequestParam List<Long> ids){
        log.info("批量删除:{}",ids);

        dishService.deleteBetch(ids);
        cleanCache("dish_*");
        return Result.success();
    }


    // 清理缓存的方法
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}

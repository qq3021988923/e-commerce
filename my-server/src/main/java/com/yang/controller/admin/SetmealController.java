package com.yang.controller.admin;

import com.yang.dto.SetmealDTO;
import com.yang.dto.SetmealPageQueryDTO;
import com.yang.entity.Setmeal;
import com.yang.result.PageResult;
import com.yang.result.Result;
import com.yang.service.SetmealService;
import com.yang.vo.SetmealVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Tag( name = "套餐相关接口" )
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    @GetMapping("/page")
    @Operation(summary = "分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO dto){
        log.info("套餐分页查询:{}",dto);

        PageResult pageResult = setmealService.pageQuery(dto);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询套餐+对应菜品 X")
    public Result<List<SetmealVO>> getById(@PathVariable Long id){
        log.info("根据id查询套餐:{}",id);
        List<SetmealVO> vo = setmealService.getByIdWithDish(id);
        System.out.println("我是套餐" + vo);
        return Result.success(vo);
    }

    @PostMapping("/status")
    @Operation(summary = "套餐起售停售 X")
    @CacheEvict(cacheNames = "setmealCache", key = "#setmeal.categoryId", condition = "#setmeal.categoryId != null")
    public Result startOrStop(@RequestBody Setmeal setmeal){
        log.info("套餐起售停售:{}",setmeal);
        setmealService.startOrStop(setmeal);
        return Result.success();
    }

    @PostMapping("/save")
    @Operation(summary = "新增套餐")
   // @CacheEvict(cacheNames = "setmealCache",key = "#dto.categoryId")
    public Result save(@RequestBody SetmealDTO dto){

        log.info("新增套餐:{}",dto);
        setmealService.saveWithDish(dto);
        return Result.success();
    }

    @PostMapping("/update")
    @Operation(summary = "修改套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody SetmealDTO dto){

        log.info("修改套餐:{}",dto);
        setmealService.update(dto);
        return Result.success();
    }

    @DeleteMapping
    @Operation(summary = "批量删除套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result delete(@RequestParam List<Long> ids) {
        log.info("批量删除套餐:{}",ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }
}

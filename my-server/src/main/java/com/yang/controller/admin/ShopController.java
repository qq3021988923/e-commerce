package com.yang.controller.admin;


import com.yang.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shop")
@Tag(name = "店铺相关接口")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/{status}")
    @Operation(summary = "设置店铺的营业状态")
    public Result setStatus(@PathVariable("status") Integer status) {
        log.info("设置店铺的营业状态为：｛｝",status == 1 ?"营业中":"打样中");
        redisTemplate.opsForValue().set(KEY,status);
        System.out.println("计算机"+status);
        return Result.success();
    }


    @GetMapping("/")
    @Operation(summary = "获取店铺的营业状态")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取到店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }




}

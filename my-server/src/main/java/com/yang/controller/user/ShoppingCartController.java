package com.yang.controller.user;

import com.yang.dto.ShoppingCartDTO;
import com.yang.entity.ShoppingCart;
import com.yang.result.Result;
import com.yang.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Tag(name = "购物车相关接口")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/addCart")
    @Operation(summary = "添加购物车")
    public Result add(@RequestBody ShoppingCartDTO dto){
        shoppingCartService.addShoppingCart(dto);
        return Result.success();
    }

    // 根据用户查看购物车
    @GetMapping("/showAdd")
    @Operation(summary = "查看购物车")
    public Result<List<ShoppingCart>> showShoppingCart(){
        List<ShoppingCart> shoppingCarts = shoppingCartService.showShoppingCart();
        return Result.success(shoppingCarts);
    }

    // 根据用户清空购物车
    @GetMapping("/clean")
    @Operation(summary = "清空购物车")
    public Result clean(){
        shoppingCartService.deleteByUserId();
        return Result.success();
    }

    @PostMapping("/sub")
    @Operation(summary = "购物车减一")
    public Result subShoppingCart(@RequestBody ShoppingCartDTO dto){
        shoppingCartService.subShoppingCart(dto);

        return Result.success();
    }

}

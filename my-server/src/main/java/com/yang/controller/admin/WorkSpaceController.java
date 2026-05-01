package com.yang.controller.admin;


import com.yang.result.Result;
import com.yang.service.WorkspaceService;
import com.yang.vo.BusinessDataVO;
import com.yang.vo.DishOverViewVO;
import com.yang.vo.OrderOverViewVO;
import com.yang.vo.SetmealOverViewVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/admin/workspace")
@Slf4j
@Tag(name= "工作台相关接口")
public class WorkSpaceController {

    @Autowired
    private WorkspaceService workspaceService;


    @GetMapping("/businessData")
    @Operation(summary = "工作台今日数据查询")
    public Result<BusinessDataVO> businessData(){
        //获取当天的开始时间
        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        //获取当天的结束时间
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);

        BusinessDataVO businessDataVO = workspaceService.getBusinessData(begin, end);
        return Result.success(businessDataVO);
    }


    @GetMapping("/overviewOrders")
    @Operation(summary = "查询订单管理数据今日")
    public Result<OrderOverViewVO> orderOverView(){

        return Result.success(workspaceService.getOrderOverView());
    }

    @GetMapping("/overviewDishes")
    @Operation(summary = "查询菜品状态（统计）")
    public Result<DishOverViewVO> dishOverView(){
        return Result.success(workspaceService.getDishOverView());
    }

    @GetMapping("/overviewsetmeal")
    @Operation(summary = "查询套餐状态统计")
    public Result<SetmealOverViewVO> setmealOverView(){
        return Result.success(workspaceService.getSetmealOverView());
    }

}

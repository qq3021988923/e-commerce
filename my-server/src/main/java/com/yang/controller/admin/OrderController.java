package com.yang.controller.admin;


import com.yang.dto.OrdersCancelDTO;
import com.yang.dto.OrdersConfirmDTO;
import com.yang.dto.OrdersPageQueryDTO;
import com.yang.dto.OrdersRejectionDTO;
import com.yang.result.PageResult;
import com.yang.result.Result;
import com.yang.service.OrderService;
import com.yang.vo.OrderStatisticsVO;
import com.yang.vo.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order")
@Tag(name = "订单管理接口")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    @Operation(summary = "订单搜索")
    public Result<PageResult> page(OrdersPageQueryDTO dto) {

        PageResult pageResult = orderService.conditionSearch(dto);

        return Result.success(pageResult);
    }

    @GetMapping("/statics")
    @Operation(summary = "各个状态的订单数量统计")
    public Result<OrderStatisticsVO> statistics() {

        return Result.success(orderService.statistics());
    }

    @GetMapping("/details/{id}")
    @Operation(summary = "查询订单详情")
    public Result<OrderVO> details(@PathVariable Long id) {

        return Result.success(orderService.details(id));
    }

    // 接单=修改订单状态
    @PostMapping("/confirm")
    @Operation(summary = "接单")
    public Result confirm(@RequestBody OrdersConfirmDTO dto) {

        orderService.confirm(dto);

        return Result.success();
    }

    // 送单
    @GetMapping("/delivery/{id}")
    @Operation(summary = "送单")
    public Result delivery(@PathVariable Long id) {

        orderService.delivery(id);
        return Result.success();
    }

    @GetMapping("/complete/{id}")
    @Operation(summary = "完成订单")
    public Result complete(@PathVariable Long id) {

        orderService.complete(id);
        return Result.success();
    }

    //  商家拒单
    @PostMapping("/reject")
    @Operation(summary = "商家拒单")
    public Result rejection(@RequestBody OrdersRejectionDTO dto) throws Exception {
        orderService.rejection(dto);
        return Result.success();
    }

    // 退款
    @PostMapping("/cancel")
    @Operation(summary = "退款")
    public Result cancel(@RequestBody OrdersCancelDTO dto) throws Exception {
        orderService.cancel(dto);
        return Result.success();
    }

}
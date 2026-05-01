package com.yang.controller.user;

import com.yang.dto.OrdersPaymentDTO;
import com.yang.dto.OrdersSubmitDTO;
import com.yang.result.Result;
import com.yang.service.OrderService;
import com.yang.vo.OrderPaymentVO;
import com.yang.vo.OrderSubmitVO;
import com.yang.vo.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/order")
@Tag(name = "订单相关接口")
@Slf4j
public class UserOrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    @Operation(summary = "用户提交订单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO dto){

        OrderSubmitVO vovo = orderService.submitOrder(dto);
        return Result.success(vovo);
    }


    @PostMapping("/payment") // 微信订单支付 未操作数据
    @Operation(summary = "微信支付，未操作数据库")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO dto) throws Exception {

        OrderPaymentVO payment = orderService.payment(dto);
        log.info("生成预支付交易单：{}", payment);
        return Result.success(payment);
    }


    @GetMapping("/historyOrders")
    @Operation(summary = "查询历史订单状态")
    public Result<List<OrderVO>> historyOrders(@RequestParam(defaultValue="1") Integer  pageNum,@RequestParam(defaultValue="10") Integer  pageSize,Integer status){

        System.out.println("历史状态" + status);
        List<OrderVO> orderVOS = orderService.pageQuerysUser(pageNum,pageSize,status);
        return Result.success(orderVOS);
    }

    @GetMapping("/orderDetail/{id}")
    @Operation(summary = "根据订单id,查询订单+详情")
    public Result<OrderVO> details(@PathVariable("id") Long id) {

        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    @GetMapping("/cancel/{id}")
    @Operation(summary = "根据订单id取消订单")
    public Result userCancelById(@PathVariable Long id) throws Exception {

        orderService.userCancelById(id);
        return Result.success();
    }

    @GetMapping("/repeat/{id}") // 之前下单过,现在点再来一单
    @Operation(summary = "之前下单过,现在点再来一单")
    public Result repetition(@PathVariable Long id){
        orderService.repetition(id);

        return Result.success();
    }
    // 催单
    @GetMapping("/reminder/{id}")
    @Operation(summary = "催单")
    public Result<Map<String,Object>> reminder(@PathVariable("id") Long id){
        return Result.success( orderService.reminder(id));
    }

}

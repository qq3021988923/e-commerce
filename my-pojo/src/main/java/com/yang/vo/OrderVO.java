package com.yang.vo;

import com.yang.entity.OrderDetail;
import com.yang.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO extends Orders implements Serializable {
    private String orderDishes;
    private List<OrderDetail> orderDetailList;
}
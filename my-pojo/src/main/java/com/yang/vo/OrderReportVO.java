package com.yang.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReportVO implements Serializable {

    private String dateList;           // 日期，逗号分隔
    private String orderCountList;     // 每日订单数，逗号分隔
    private String validOrderCountList;// 每日有效订单数，逗号分隔
    private Integer totalOrderCount;   // 订单总数
    private Integer validOrderCount;   // 有效订单数
    private Double orderCompletionRate;// 订单完成率

}
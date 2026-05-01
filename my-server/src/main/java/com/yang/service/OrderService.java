package com.yang.service;

import com.yang.dto.*;
import com.yang.result.PageResult;
import com.yang.vo.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderService {

    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statistics();

     OrderVO details(Long id);

      void confirm(OrdersConfirmDTO DTO);

     void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

     void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;

      void delivery(Long id);

      void complete(Long id);

     OrderSubmitVO submitOrder(OrdersSubmitDTO dto);

     OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

     List<OrderVO> pageQuerysUser(int  pageNum, int  pageSize, Integer status);

    public void userCancelById(Long id) throws Exception;

    public void repetition(Long id);

    public Map<String,Object> reminder(Long id);
}

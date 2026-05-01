package com.yang.mapper;

import com.github.pagehelper.Page;
import com.yang.annotation.AutoFill;
import com.yang.dto.GoodsSalesDTO;
import com.yang.dto.OrdersCancelDTO;
import com.yang.dto.OrdersPageQueryDTO;
import com.yang.dto.OrdersRejectionDTO;
import com.yang.entity.Orders;
import com.yang.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

      void insert(Orders orders);

      void update(Orders orders);

      Orders getById(Long id);

      Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

      Integer countStatus(Integer status);

      Double sumByMap(Map map);

      Integer countByMap(Map map);

      List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);

      List<Orders> getByStatusAndOrderTime(Integer status, LocalDateTime orderTime);
}

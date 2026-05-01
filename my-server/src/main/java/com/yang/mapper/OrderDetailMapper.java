package com.yang.mapper;

import com.yang.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

      void insertBatch(List<OrderDetail> orderDetailList);

      List<OrderDetail> getByOrderId(Long orderId);

}

package com.yang.service;

import com.yang.vo.BusinessDataVO;
import com.yang.vo.DishOverViewVO;
import com.yang.vo.OrderOverViewVO;
import com.yang.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {

    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    public OrderOverViewVO getOrderOverView();

    public DishOverViewVO getDishOverView();

    public SetmealOverViewVO getSetmealOverView();
}

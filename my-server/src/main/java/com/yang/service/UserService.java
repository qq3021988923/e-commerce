package com.yang.service;


import com.yang.dto.UserLoginDTO;
import com.yang.entity.ShoppingCart;
import com.yang.entity.User;
import com.yang.vo.UserLoginVO;

import java.util.List;

public interface UserService {

    UserLoginVO login(UserLoginDTO dto);


}

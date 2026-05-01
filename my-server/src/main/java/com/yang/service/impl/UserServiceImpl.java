package com.yang.service.impl;

import com.yang.constant.JwtClaimsConstant;
import com.yang.context.BaseContext;
import com.yang.dto.UserLoginDTO;
import com.yang.entity.ShoppingCart;
import com.yang.entity.User;
import com.yang.exception.BaseException;
import com.yang.mapper.UserMapper;
import com.yang.properties.JwtProperties;
import com.yang.result.Result;
import com.yang.service.UserService;
import com.yang.utils.JwtUtil;
import com.yang.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;


    @Override
    public UserLoginVO login(UserLoginDTO dto) {

        User u = userMapper.getByNameAndidNumber(dto);

        if(u == null){

           throw new BaseException("登录错误");
        }

        Map<String,Object> map=new HashMap<String,Object>();
        map.put(JwtClaimsConstant.USER_ID,u.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), map);
        UserLoginVO v=UserLoginVO.builder()
                .id(u.getId())
                .openid(u.getOpenid())
                .token(token)
                .build();

        return v;
    }




}

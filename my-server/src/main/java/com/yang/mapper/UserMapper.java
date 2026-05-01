package com.yang.mapper;


import com.yang.annotation.AutoFill;
import com.yang.dto.UserLoginDTO;
import com.yang.entity.User;
import com.yang.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    Integer countByMap(Map map);

    @AutoFill(OperationType.INSERT)
    void insert(User user);

    User getById(Long id);

    User getByOpenid(String openid);

    User getByNameAndidNumber(UserLoginDTO dto);


}

package com.yang.mapper;

import com.yang.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    List<ShoppingCart> list(ShoppingCart s);

    void updateNumberById(ShoppingCart s);

    void insert(ShoppingCart shoppingCart);

    void deleteByUserId(Long userId);

    void deleteById(Long id);

    void insertBatch(List<ShoppingCart> shoppingCartList);
}

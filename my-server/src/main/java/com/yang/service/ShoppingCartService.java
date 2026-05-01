package com.yang.service;

import com.yang.dto.ShoppingCartDTO;
import com.yang.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    List<ShoppingCart> list(ShoppingCart s);

    void addShoppingCart(ShoppingCartDTO dto);

    public List<ShoppingCart> showShoppingCart();

    void deleteByUserId();

    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);


}

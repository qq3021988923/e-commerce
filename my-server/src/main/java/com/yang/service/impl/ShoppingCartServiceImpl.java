package com.yang.service.impl;

import com.yang.context.BaseContext;
import com.yang.dto.ShoppingCartDTO;
import com.yang.entity.Dish;
import com.yang.entity.Setmeal;
import com.yang.entity.ShoppingCart;
import com.yang.mapper.DishMapper;
import com.yang.mapper.SetmealMapper;
import com.yang.mapper.ShoppingCartMapper;
import com.yang.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public List<ShoppingCart> list(ShoppingCart s) {
        return shoppingCartMapper.list(s);
    }

    @Override
    public void addShoppingCart(ShoppingCartDTO s) {
        //判断当前加入到购物车中的商品是否已经存在了
        // 有数据：判断 就在数量number的字段添加1 调用接口
        // 或 判断是否是菜品 是否套餐 赋值名字，图片，价格
        ShoppingCart cart=new ShoppingCart();
        BeanUtils.copyProperties(s,cart);
        cart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = shoppingCartMapper.list(cart);
        System.out.println(s);
        System.out.println("查询购物车" + list);

        if(list !=null && !list.isEmpty()){ // +1
            ShoppingCart sp = list.get(0);
            sp.setNumber(sp.getNumber()+1);
            shoppingCartMapper.updateNumberById(sp);
        }else{
            Long dishId = s.getDishId();
            Dish dish = dishMapper.getById(dishId);
            if(dishId !=null){
                cart.setName(dish.getName());
                cart.setImage(dish.getImage());
                cart.setAmount(dish.getPrice());
            }else{
                Long setmealId = s.getSetmealId();
                Setmeal setmeal = setmealMapper.getById(setmealId);
                cart.setName(setmeal.getName());
                cart.setImage(setmeal.getImage());
                cart.setAmount(setmeal.getPrice());
            }
            cart.setNumber(1);
            cart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(cart);
        }

    }

    @Override
    public List<ShoppingCart> showShoppingCart() {

        Long userId = BaseContext.getCurrentId();
        ShoppingCart s=ShoppingCart.builder()
                .userId(userId)
                .build();
        return shoppingCartMapper.list(s);
    }

    @Override
    public void deleteByUserId() {

        Long uId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(uId);
    }

    @Override // 不用传用户id，直接从上下文拿取
    public void subShoppingCart(ShoppingCartDTO dto) {

        Long userId = BaseContext.getCurrentId();
        dto.setUserId(userId);
        ShoppingCart s=new ShoppingCart();
        BeanUtils.copyProperties(dto,s);
        List<ShoppingCart> list = shoppingCartMapper.list(s);

        if(list !=null && !list.isEmpty()){

            ShoppingCart sp = list.get(0);
            if(sp.getNumber()==1){

                shoppingCartMapper.deleteById(sp.getId());
            }else{

                sp.setNumber(sp.getNumber()-1);
                shoppingCartMapper.updateNumberById(sp);
            }
        }
    }
}

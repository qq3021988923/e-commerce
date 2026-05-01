package com.yang.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yang.constant.StatusConstant;
import com.yang.dto.DishPageQueryDTO;
import com.yang.entity.Dish;
import com.yang.entity.DishFlavor;
import com.yang.exception.BaseException;
import com.yang.mapper.DishFlavorMapper;
import com.yang.mapper.DishMapper;
import com.yang.mapper.SetmealDishMapper;
import com.yang.result.PageResult;
import com.yang.service.DishService;
import com.yang.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageResult pageQuery(DishPageQueryDTO dto) {
        // PageHelper.startPage 当前是第几页、每页多少条。
        // 里放了页码和条数，MyBatis 执行 SQL 前会被拦截器拦截,SQL改写带 LIMIT 的分页语句
        PageHelper.startPage(dto.getPage(),dto.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dto);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public DishVO getByIdWithFlavor(Long id) {
        // 根据id查询主表和子表的数据，通过BeanUtil属性赋值的拷贝到vo包装类里面
        Dish d = dishMapper.getById(id);
        List<DishFlavor> f = dishFlavorMapper.getByDishId(d.getId());
        DishVO vo=new DishVO();
        BeanUtils.copyProperties(d,vo);
        vo.setFlavors(f);
        return vo;
    }

    @Override
    public List<Dish> list(Long categoryId) {
        Dish d=Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(d);
    }

    @Override
    @Transactional
    public void saveWithFlavor(DishVO dto) {

        // 直接插入主表，数据回写，通过id关联批量插入子表，清空缓存
        Dish d=new Dish();
        BeanUtils.copyProperties(dto,d);

        dishMapper.insert(d);
        Long did = d.getId();

        List<DishFlavor> flavors = dto.getFlavors();
        if(flavors !=null && !flavors.isEmpty()){
            flavors.forEach(f->{
                f.setDishId(did);
            });

            dishFlavorMapper.insertBatch(flavors);
        }

        // 清理缓存 - 新增菜品后，删除对应分类的缓存
        String cacheKey = "dish_" + dto.getCategoryId();
        redisTemplate.delete(cacheKey);

    }

    @Override
    @Transactional
    public void update(DishVO v) {
        // 直接修主，通过主id删除详情，关联主表id重新插入子表，清空缓存
        Dish d=new Dish();
        BeanUtils.copyProperties(v,d);
        dishMapper.update(d);

        Long did = d.getId();
        dishFlavorMapper.deleteByDishId(did);

        List<DishFlavor> flavors = v.getFlavors();
        if(flavors !=null && !flavors.isEmpty()){
            flavors.forEach(f->{
                f.setDishId(did);
            });
            dishFlavorMapper.insertBatch(flavors);
        }

        // 清理缓存 - 修改菜品后，删除对应分类的缓存
        String cacheKey = "dish_" + v.getCategoryId();
        redisTemplate.delete(cacheKey);

    }


    @Override
    @Transactional
    public void deleteBetch(List<Long> ids) {
        // 商品启用状态下不能删除 抛，在套餐有关联的商品下 抛
        ids.forEach(d->{
            Dish dish = dishMapper.getById(d);

            if(dish == null){
                throw new BaseException("菜品不存在");
            }

            if(dish.getStatus() == StatusConstant.ENABLE){
                throw new BaseException("当前菜品起售中，不能删除");
            }
        });

        List<Long> setmealIdsByDishIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(setmealIdsByDishIds !=null && !setmealIdsByDishIds.isEmpty()){
            throw new BaseException("当前菜品关联套餐，不能删除");
        }

        ids.forEach(d->{
            System.out.println("菜品ids" + d);
            // 获取菜品信息，用于清理缓存
            Dish dish = dishMapper.getById(d);
            if (dish != null) {
                // 清理缓存 - 删除菜品后，删除对应分类的缓存
                String cacheKey = "dish_" + dish.getCategoryId();
                redisTemplate.delete(cacheKey);
            }
            dishMapper.deleteById(d);
            dishFlavorMapper.deleteByDishId(d);
        });
    }

    @Override
    public List<DishVO> listWithFlavor(Dish d){
        // 先从缓存拿 未命中 从数据库读，再写入缓存，设置过期时间
        // 查主表 ，根据主表id查询子表，通过属性赋值到包装类
        List<Dish> list = dishMapper.list(d);

        List<DishVO> vos = new ArrayList<>();
        for(Dish df:list){

            DishVO dishVo=new DishVO();
            BeanUtils.copyProperties(df,dishVo);
            List<DishFlavor> dflavof = dishFlavorMapper.getByDishId(df.getId());
            dishVo.setFlavors(dflavof);

            vos.add(dishVo);
        }

        return vos;
    }

    @Override
    public void getSetMealStatusbyDishId(Long id,Integer status) {
        // 设置禁用状态时，先查询当前菜品是否在套餐中有启用售卖，有就抛异常
        // 没有就正常执行
        Dish dish=null;
        if (status == StatusConstant.DISABLE) { // 0
            Integer i = dishMapper.countOnSaleSetmealByDishId(id);
        if (i != null && i > 0) {
            log.info("根据菜品id统计套餐status等于1的数量：{}",i);
            throw new BaseException("该菜品被 " + i + " 个在售套餐引用，请先停售相关套餐");

            }
        }

        dish=Dish.builder().status(status)
                .id(id)
                .build();
        dishMapper.update(dish);


    }

}

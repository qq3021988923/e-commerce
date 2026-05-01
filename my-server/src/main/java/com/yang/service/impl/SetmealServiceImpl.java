package com.yang.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yang.constant.StatusConstant;
import com.yang.dto.SetmealDTO;
import com.yang.dto.SetmealPageQueryDTO;
import com.yang.entity.Dish;
import com.yang.entity.Setmeal;
import com.yang.entity.SetmealDish;
import com.yang.exception.BaseException;
import com.yang.mapper.DishMapper;
import com.yang.mapper.SetmealDishMapper;
import com.yang.mapper.SetmealMapper;
import com.yang.result.PageResult;
import com.yang.service.SetmealService;
import com.yang.vo.DishItemVO;
import com.yang.vo.SetmealOverViewVO;
import com.yang.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO dto) {
        // 使用PageHelper.startPage 插件 自定义第几页、每页几条，
        // 调用数据库sql的时候自定会添加limit分页查询的
        PageHelper.startPage(dto.getPage(),dto.getPageSize());
        Page<SetmealVO> setmealVOS = setmealMapper.pageQuery(dto);

        return new PageResult(setmealVOS.getTotal(),setmealVOS.getResult());
    }

    @Override
    public List<SetmealVO> getByIdWithDish(Long id) {
        return setmealMapper.getByIdWithDish(id);
    }

    @Override
    public void startOrStop(Setmeal s) {

        if(s.getStatus() == StatusConstant.ENABLE){
            List<Dish> d = dishMapper.getBySetmealId(s.getId());
            if(d !=null && !d.isEmpty()){
                d.forEach(dish ->{
                   if(dish.getStatus() == StatusConstant.DISABLE){
                       throw new BaseException("套餐内包含未启售菜品，无法启售");
                   }
                });
            }
        }

        setmealMapper.update(s);

    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public void saveWithDish(SetmealDTO dto) {
        // 插入主表,主键回写，根据主表id关联子表，批量插入子表数据
        // 删除缓存
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(dto,setmeal);
        setmealMapper.insert(setmeal);

        Long id = setmeal.getId(); // 主键回写

        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        setmealDishes.forEach(sd -> {
            sd.setSetmealId(id);
        });

        setmealDishMapper.insertBatch(setmealDishes);

    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public void update(SetmealDTO setmealDTO) {
        // 更新主表，通过主表id删除子表数据，
        // 根据主表id关联批量插入数据 ，删除缓存，
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        setmealMapper.update(setmeal);
        Long id = setmealDTO.getId();

        setmealDishMapper.deleteBySetmealId(id);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(sd -> {
            sd.setSetmealId(id);
        });

        setmealDishMapper.insertBatch(setmealDishes);

    }

    @Override
    @Transactional // 起售状态的套餐不能删除
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public void deleteBatch(List<Long> ids) {
            // 启用状态不能删除，根据套餐id删除主表和子表的数据
        if (ids == null || ids.isEmpty()) {
            throw new BaseException("删除的套餐ID列表不能为空");
        }

        ids.forEach(id ->{
            Setmeal sid = setmealMapper.getById(id);
            if(sid.getStatus() == StatusConstant.ENABLE){
                throw new BaseException("起售状态不能删除");
            }
            setmealMapper.deleteById(id);
            setmealDishMapper.deleteBySetmealId(id);
        });
    }

    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        return setmealMapper.list(setmeal);
    }

    @Override
    public List<DishItemVO> getDishItemBySetmealId(Long setmealId) {
        return setmealMapper.getDishItemBySetmealId(setmealId);
    }


}

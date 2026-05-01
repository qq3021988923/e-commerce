package com.yang.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yang.constant.StatusConstant;
import com.yang.dto.CategoryPageQueryDTO;
import com.yang.entity.Category;
import com.yang.exception.BaseException;
import com.yang.mapper.CategoryMapper;
import com.yang.mapper.DishMapper;
import com.yang.mapper.SetmealMapper;
import com.yang.result.PageResult;
import com.yang.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {


    //插入状态默认禁用 StatusConstant.D
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void save(Category category) {
        category.setStatus(StatusConstant.DISABLE);
        System.out.println("我是分类"+category);
        categoryMapper.insert(category);
    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO dto) {
        PageHelper.startPage(dto.getPage(),dto.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(dto);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }

    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public void delete(Long id) {
        Integer i = dishMapper.countByCategoryId(id);

        if( i>0 ){
            throw new BaseException("当前分类有关联菜品不能删除");
        }

        Integer i1 = setmealMapper.countByCategoryId(id);
        if( i1>0 ){
            throw new BaseException("当前分类有关联套餐不能删除");
        }
        categoryMapper.deleteById(id);
    }


}

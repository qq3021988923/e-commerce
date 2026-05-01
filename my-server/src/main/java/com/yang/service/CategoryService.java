package com.yang.service;
import com.yang.dto.CategoryPageQueryDTO;
import com.yang.entity.Category;
import com.yang.result.PageResult;

import java.util.List;


public interface CategoryService {

    void save(Category category);

    PageResult pageQuery(CategoryPageQueryDTO dto);

    List<Category> list(Integer type);

    void update(Category category);

    void delete(Long id);

    //void startOrStop(Category category);
}

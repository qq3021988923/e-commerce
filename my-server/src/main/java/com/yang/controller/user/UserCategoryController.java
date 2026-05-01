package com.yang.controller.user;

import com.yang.entity.Category;
import com.yang.result.Result;
import com.yang.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/category")
@Tag(name = "分类接口")
public class UserCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @Operation(summary = "根据类型查询分类列表")
    public Result<List<Category>> list(Integer type){
        return Result.success(categoryService.list(type));
    }

}

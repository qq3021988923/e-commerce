package com.yang.controller.admin;

import com.yang.dto.CategoryPageQueryDTO;
import com.yang.entity.Category;
import com.yang.result.PageResult;
import com.yang.result.Result;
import com.yang.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Tag( name = "分类相关接口")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // http://localhost:1234/category/save
    @PostMapping("save")
    @Operation( summary="新增分类")
    public Result save(@RequestBody Category category) {
        log.info("新增分类：{}", category);
        categoryService.save(category);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation( summary="分类/分页查询")
    public PageResult page(CategoryPageQueryDTO dto) {
        log.info("分页查询：{}", dto);
        return categoryService.pageQuery(dto);
    }

    @GetMapping("/list/{type}")
    @Operation( summary="根据类型查询分类")
    public Result<List<Category>> list(@PathVariable Integer type) {
        log.info("根据类型查询分类：{}", type);
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }

    @PostMapping("/update")
    @Operation( summary="修改分类/启用禁用")
    public Result<String> update(@RequestBody Category category) {
        log.info("根据类型查询分类：{}", "修改分类/启用禁用");
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除分类")
    public Result<String> deleteById(Long id) {
        log.info("删除分类：{}", id);
        categoryService.delete(id);
        return Result.success("null");
    }



}

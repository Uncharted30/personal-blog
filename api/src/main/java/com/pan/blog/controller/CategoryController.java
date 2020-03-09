package com.pan.blog.controller;

import com.pan.blog.annotation.OperationLog;
import com.pan.blog.annotation.VisitLog;
import com.pan.blog.entities.Category;
import com.pan.blog.service.CategoryService;
import com.pan.blog.utils.Result;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping(value = {"/categories/list/{pageSize}/{page}/{keyword}", "/categories/list/{pageSize}/{page}"})
  @VisitLog
  public Result categories(
      @PathVariable Integer pageSize,
      @PathVariable(required = false) Integer page,
      @PathVariable(required = false) String keyword) {
    page = page == null ? 0 : page;
    return new Result(this.categoryService.listCategory(pageSize, page, keyword));
  }

  @GetMapping("/categories")
  public Result findAllCategories() {
    return new Result(this.categoryService.findAllCategories());
  }

  @GetMapping("/categories/{id}")
  public Result findCategoryById(@PathVariable Long id) {
    return new Result(this.categoryService.findCategoryById(id));
  }

  @PostMapping("/admin/category")
  @OperationLog("Add category")
  public Result addCategory(@Valid @RequestBody Category category) {
    Category existingCategory = this.categoryService.findCategoryByName(category.getName());
    if (existingCategory != null) {
      return new Result(-1, "Category exists");
    }

    this.categoryService.saveCategory(category);
    return new Result();
  }

  @DeleteMapping("/admin/category/{id}")
  @OperationLog("Delete category")
  public Result deleteCategory(@PathVariable Long id) {
    this.categoryService.deleteCategory(id);
    return new Result();
  }

  @PutMapping("/admin/category")
  @OperationLog("Update category")
  public Result updateCategory(@RequestBody Category category) {
    this.categoryService.updateCategory(category);
    return new Result();
  }
}

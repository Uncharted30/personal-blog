package com.pan.blog.service.interfaces;

import com.pan.blog.entities.Category;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {

  Category findCategoryByName(String name);

  Category findCategoryById(Long id);

  void saveCategory(Category category);

  Page<Category> listCategory(Integer pageSize, Integer page, String keyword);

  void updateCategory(Category category);

  void deleteCategory(Long id);

  List<Category> findAllCategories();
}

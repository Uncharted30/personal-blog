package com.pan.blog.dao;

import com.pan.blog.entities.Blog;
import com.pan.blog.entities.Category;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long>,
    JpaSpecificationExecutor<Category> {

  Category getCategoryByName(String name);

  default Category findByIdOrNull(Long id) {
    return findById(id).orElse(null);
  }
}

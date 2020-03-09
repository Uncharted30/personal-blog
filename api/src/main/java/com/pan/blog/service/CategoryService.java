package com.pan.blog.service;

import com.pan.blog.dao.CategoryRepository;
import com.pan.blog.entities.Category;
import com.pan.blog.exceptions.PageOutOfBoundException;
import com.pan.blog.service.interfaces.ICategoryService;
import com.pan.blog.utils.PageUtil;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService implements ICategoryService {

  @Autowired
  CategoryRepository categoryRepository;

  @Override
  public Category findCategoryByName(String name) {
    return this.categoryRepository.getCategoryByName(name);
  }

  @Override
  public Category findCategoryById(Long id) {
    Category category = this.categoryRepository.findByIdOrNull(id);
    if (category == null) {
      throw new EntityNotFoundException();
    }
    return category;
  }

  @Override
  public void saveCategory(Category category) {
    this.categoryRepository.save(category);
  }

  @Override
  public Page<Category> listCategory(Integer pageSize, Integer page, String keyword) {
    PageUtil.validPage(pageSize, page, this.categoryRepository);
    Sort sort = Sort.by(Direction.DESC, "id");
    Pageable pageable = PageRequest.of(page, pageSize, sort);
    if (keyword != null && !keyword.isEmpty()) {
      return this.categoryRepository.findAll(new Specification<Category>() {
        @Override
        public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> criteriaQuery,
            CriteriaBuilder criteriaBuilder) {
          return criteriaBuilder.like(root.get("name").as(String.class), "%" + keyword + "%");
        }
      }, pageable);
    }
    return this.categoryRepository.findAll(pageable);
  }

  @Override
  public void updateCategory(Category category) {
    Category oldCategory = this.findCategoryById(category.getId());
    if (oldCategory == null) {
      throw new EntityNotFoundException("Cannot find the category");
    }
    BeanUtils.copyProperties(category, oldCategory);
    this.categoryRepository.save(category);
  }

  @Override
  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }

  @Override
  public List<Category> findAllCategories() {
    return this.categoryRepository.findAll();
  }
}

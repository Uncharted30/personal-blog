package com.pan.blog.service;

import com.pan.blog.dao.BlogrollRepository;
import com.pan.blog.entities.Blogroll;
import com.pan.blog.exceptions.CommonException;
import com.pan.blog.service.interfaces.IBlogrollService;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BlogrollService implements IBlogrollService {

  @Autowired
  private BlogrollRepository blogrollRepository;

  @Override
  public List<Blogroll> findAllBlogroll(String keyword) {
    if (keyword != null && !keyword.isEmpty()) {
      return this.blogrollRepository.findAll(new Specification<Blogroll>() {
        @Override
        public Predicate toPredicate(Root<Blogroll> root, CriteriaQuery<?> criteriaQuery,
            CriteriaBuilder criteriaBuilder) {
          return criteriaBuilder.like(root.get("name").as(String.class), "%" + keyword + "%");
        }
      });
    } else {
      return this.blogrollRepository.findAll();
    }
  }

  @Override
  public void addBlogroll(Blogroll blogroll) {
    if (this.blogrollRepository.count() > 10) {
      throw new CommonException("Maximum number of blogrolls is 10. You cannot add more blogrolls.");
    }
    this.blogrollRepository.save(blogroll);
  }

  @Override
  public void deleteBlogroll(Long id) {
    this.blogrollRepository.deleteById(id);
  }

  @Override
  public void updateBlogroll(Blogroll blogroll) {
    this.blogrollRepository.save(blogroll);
  }
}

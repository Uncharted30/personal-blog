package com.pan.blog.service;

import com.pan.blog.dao.BlogRepository;
import com.pan.blog.entities.Blog;
import com.pan.blog.entities.Category;
import com.pan.blog.entities.Tag;
import com.pan.blog.service.interfaces.IBlogService;
import com.pan.blog.utils.PageUtil;
import com.pan.blog.vo.BlogQuery;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class BlogService implements IBlogService {

  @Autowired
  private BlogRepository blogRepository;

  @Override
  @Transactional
  public Blog findBlogById(Long id) {
    Blog blog = this.blogRepository.findBlogByIdOrNull(id);
    if (blog == null) {
      throw new EntityNotFoundException("Blog not found");
    }
    this.blogRepository.updateViews(blog.getId());
    return blog;
  }

  @Override
  public Page<Blog> listBlog(Integer pageSize, Integer page, BlogQuery blogQuery, Boolean display) {
    PageUtil.validPage(pageSize, page, this.blogRepository);
    Sort sort = Sort.by(Direction.DESC, "createTime");
    Pageable pageable = PageRequest.of(page, pageSize, sort);
    Page<Blog> blogPage = null;
    if (blogQuery != null && (blogQuery.getCategoryId() != null || !StringUtils
        .isEmpty(blogQuery.getTitle()))) {
      blogPage = this.blogRepository.findAll(new Specification<Blog>() {
        @Override
        public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery,
            CriteriaBuilder criteriaBuilder) {
          Path<Object> title = root.get("title");
          Path<Object> categoryId = root.<Category>get("category").get("id");
          Path<Object> content = root.get("content");
          String queryString = "%" + blogQuery.getTitle() + "%";
          System.out.println(queryString);

          Predicate predicate1 = null;
          Predicate predicate2 = null;

          if (blogQuery.getTitle() != null && !blogQuery.getTitle().isEmpty()) {
            predicate1 = criteriaBuilder.like(title.as(String.class), queryString);
            predicate1 = criteriaBuilder
                .or(criteriaBuilder.like(content.as(String.class), queryString), predicate1);
          }
          if (blogQuery.getCategoryId() > 0) {
            predicate2 = criteriaBuilder
                .equal(categoryId.as(Long.class), blogQuery.getCategoryId());
          }
          Predicate predicate;
          if (predicate1 != null && predicate2 != null) {
            predicate = criteriaBuilder.and(predicate1, predicate2);
          } else {
            predicate = predicate1 == null ? predicate2 : predicate1;
          }
          if (display) {
            return criteriaBuilder.and(predicate, criteriaBuilder.isFalse(root.get("draft").as(Boolean.class)));
          }
          return predicate;
        }
      }, pageable);
    } else {
      blogPage = this.blogRepository.findAll(new Specification<Blog>() {
        @Override
        public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery,
            CriteriaBuilder criteriaBuilder) {
          if (display) {
            return criteriaBuilder.isFalse(root.get("draft").as(Boolean.class));
          }
          return null;
        }
      }, pageable);
    }
    for (Blog blog : blogPage) {
      blog.setContentMd("");
      if (blog.getContent().length() > 100) {
        blog.setContent(blog.getContent().substring(0, 90) + "...");
      }
    }
    return blogPage;
  }

  @Override
  public void saveBlog(Blog blog) {
    blog.setViews(0);
    this.blogRepository.save(blog);
  }

  @Override
  public void updateBlog(Blog blog) {
    Blog oldBlog = this.blogRepository.findBlogByIdOrNull(blog.getId());
    if (oldBlog == null) {
      throw new EntityNotFoundException();
    }
    oldBlog.setHeaderImgUrl(blog.getHeaderImgUrl());
    oldBlog.setTitle(blog.getTitle());
    oldBlog.setCategory(blog.getCategory());
    oldBlog.setContent(blog.getContent());
    oldBlog.setContentMd(blog.getContentMd());
    oldBlog.setTags(blog.getTags());
    oldBlog.setDraft(blog.getDraft());
    this.blogRepository.save(oldBlog);
  }

  @Override
  public void deleteBlog(Long id) {
    this.blogRepository.deleteById(id);
  }

  @Override
  public List<List<Blog>> getBlogArchive() {
    List<Blog> blogList = this.blogRepository.findAll(Sort.by(Direction.DESC, "createTime"));
    if (blogList.isEmpty()) {
      return new ArrayList<>();
    }
    List<Blog> sameMonth = new ArrayList<>();
    sameMonth.add(blogList.get(0));
    Calendar recent = Calendar.getInstance();
    recent.setTime(blogList.get(0).getCreateTime());
    Integer recentYear = recent.get(Calendar.YEAR);
    Integer recentMonth = recent.get(Calendar.MONTH);
    Calendar curr = Calendar.getInstance();
    List<List<Blog>> archive = new ArrayList<>();
    for (int i = 1; i < blogList.size(); i++) {
      curr.setTime(blogList.get(i).getCreateTime());
      if (curr.get(Calendar.YEAR) != recentYear || curr.get(Calendar.MONTH) != recentMonth) {
        archive.add(sameMonth);
        sameMonth = new ArrayList<>();
      }
      sameMonth.add(blogList.get(i));
      recentYear = curr.get(Calendar.YEAR);
      recentMonth = curr.get(Calendar.MONTH);
    }
    archive.add(sameMonth);
    return archive;
  }

  @Override
  public List<Blog> getBlogByCategory(Long categoryId) {
    Category queryCategory = new Category();
    queryCategory.setId(categoryId);
    return this.blogRepository.findAllByCategory(queryCategory);
  }

  @Override
  public List<Blog> getBlogByTag(Long tagId) {
    Tag queryTag = new Tag();
    queryTag.setId(tagId);
    return this.blogRepository.findAllByTagsContains(queryTag);
  }

  @Override
  public List<Blog> searchBlog(String keyword) {
    Sort sort = Sort.by(Direction.DESC, "createTime");
    if (!StringUtils.isEmpty(keyword)) {
      return this.blogRepository.findAll(new Specification<Blog>() {
        @Override
        public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery,
            CriteriaBuilder criteriaBuilder) {
          String queryString = "%" + keyword + "%";
          Predicate title = criteriaBuilder.like(root.get("title"), queryString);
          Predicate content = criteriaBuilder.like(root.get("content"), queryString);
          return criteriaBuilder.or(title, content);
        }
      }, sort);
    }

    return this.blogRepository.findAll(sort);
  }
}

package com.pan.blog.dao;

import com.pan.blog.entities.Category;
import com.pan.blog.entities.Tag;
import com.pan.blog.entities.dto.BlogListDto;
import com.pan.blog.entities.Blog;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

  default Blog findBlogByIdOrNull(Long id) {
    return findById(id).orElse(null);
  }

  List<Blog> findAllByCategory(Category category);

  List<Blog> findAllByTagsContains(Tag tag);

  @Modifying
  @Query("update Blog b set b.views = b.views + 1 where  b.id = ?1")
  void updateViews(Long id);
}

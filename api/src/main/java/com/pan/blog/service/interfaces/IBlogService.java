package com.pan.blog.service.interfaces;

import com.pan.blog.entities.dto.BlogListDto;
import com.pan.blog.entities.Blog;
import com.pan.blog.vo.BlogQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBlogService {

  Blog findBlogById(Long id);

  Page<Blog> listBlog(Integer pageSize, Integer page, BlogQuery blogQuery, Boolean display);

  void saveBlog(Blog blog);

  void updateBlog(Blog blog);

  void deleteBlog(Long id);

  List<List<Blog>> getBlogArchive();

  List<Blog> getBlogByCategory(Long categoryId);

  List<Blog> getBlogByTag(Long tagId);

  List<Blog> searchBlog(String keyword);
}

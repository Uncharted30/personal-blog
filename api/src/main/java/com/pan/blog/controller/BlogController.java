package com.pan.blog.controller;

import com.pan.blog.annotation.OperationLog;
import com.pan.blog.annotation.VisitLog;
import com.pan.blog.entities.Blog;
import com.pan.blog.exceptions.PageOutOfBoundException;
import com.pan.blog.service.BlogService;
import com.pan.blog.utils.Result;
import com.pan.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

  @Autowired
  private BlogService blogService;

  @GetMapping({"/admin/blog/list/{pageSize}", "/admin/blog/list/{pageSize}/{page}"})
  public Result listBlog(
      @PathVariable Integer pageSize,
      @PathVariable(required = false) Integer page,
      @RequestParam(value = "categoryId", required = false) Long categoryId,
      @RequestParam(value = "title", required = false) String title) {
    page = page == null ? 0 : page;
    BlogQuery blogQuery = new BlogQuery();
    blogQuery.setCategoryId(categoryId);
    blogQuery.setTitle(title);
    return new Result(blogService.listBlog(pageSize, page, blogQuery, false));
  }

  @GetMapping({"/blog/list/{pageSize}", "/blog/list/{pageSize}/{page}"})
  @VisitLog
  public Result displayBlog(
      @PathVariable Integer pageSize,
      @PathVariable(required = false) Integer page,
      @RequestParam(value = "categoryId", required = false) Long categoryId,
      @RequestParam(value = "title", required = false) String title) {
    page = page == null ? 0 : page;
    BlogQuery blogQuery = new BlogQuery();
    blogQuery.setCategoryId(categoryId);
    blogQuery.setTitle(title);
    return new Result(blogService.listBlog(pageSize, page, blogQuery, true));
  }

  @GetMapping("/blog/search/{keyword}")
  public Result searchBlog(@PathVariable String keyword) {
    System.out.println(keyword);
    return new Result(this.blogService.searchBlog(keyword));
  }

  @GetMapping("/blog/category/{categoryId}")
  public Result getBlogByCategory(@PathVariable Long categoryId) {
    return new Result(this.blogService.getBlogByCategory(categoryId));
  }

  @GetMapping("/blog/tag/{tagId}")
  public Result getBlogByTag(@PathVariable Long tagId) {
    return new Result(this.blogService.getBlogByTag(tagId));
  }

  @GetMapping("/blog/archive")
  @VisitLog
  public Result getBlogArchive() {
    return new Result(this.blogService.getBlogArchive());
  }

  @PostMapping("/admin/blog")
  @OperationLog("Add new blog")
  public Result saveBlog(@RequestBody Blog blog) {
    this.blogService.saveBlog(blog);
    return new Result();
  }

  @PutMapping("/admin/blog")
  @OperationLog("Update blog")
  public Result updateBlog(@RequestBody Blog blog) {
    this.blogService.updateBlog(blog);
    return new Result();
  }

  @DeleteMapping("/admin/blog/{id}")
  @OperationLog("Delete blog")
  public Result deleteBlog(@PathVariable Long id) {
    this.blogService.deleteBlog(id);
    return new Result();
  }

  @GetMapping("/blog/{id}")
  @VisitLog
  public Result findBlog(@PathVariable Long id) {
    return new Result(this.blogService.findBlogById(id));
  }
}

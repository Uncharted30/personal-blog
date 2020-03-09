package com.pan.blog.controller;

import com.pan.blog.annotation.OperationLog;
import com.pan.blog.annotation.VisitLog;
import com.pan.blog.entities.Blogroll;
import com.pan.blog.service.BlogrollService;
import com.pan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogrollController {

  @Autowired
  private BlogrollService blogrollService;

  @GetMapping({"/blogroll/{keyword}", "/blogroll/", "/blogroll"})
  public Result findAllBlogroll(@PathVariable(required = false) String keyword) {
    return new Result(this.blogrollService.findAllBlogroll(keyword));
  }

  @PostMapping("/admin/blogroll")
  @OperationLog("Add blogroll")
  public Result addBlogroll(@RequestBody Blogroll blogroll) {
    this.blogrollService.addBlogroll(blogroll);
    return new Result();
  }

  @DeleteMapping("/admin/blogroll/{id}")
  @OperationLog("Delete blogroll")
  public Result  deleteBlogroll(@PathVariable Long id) {
    this.blogrollService.deleteBlogroll(id);
    return new Result();
  }

  @PutMapping("/admin/blogroll")
  @OperationLog("Update blogroll")
  public Result updateBlogroll(@RequestBody Blogroll blogroll) {
    this.blogrollService.updateBlogroll(blogroll);
    return new Result();
  }
}

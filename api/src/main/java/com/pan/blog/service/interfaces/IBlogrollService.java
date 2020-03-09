package com.pan.blog.service.interfaces;

import com.pan.blog.entities.Blogroll;
import java.util.List;

public interface IBlogrollService {

  List<Blogroll> findAllBlogroll(String keyword);

  void addBlogroll(Blogroll blogroll);

  void deleteBlogroll(Long id);

  void updateBlogroll(Blogroll blogroll);
}

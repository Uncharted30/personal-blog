package com.pan.blog.vo;

public class BlogQuery {

  public String title;
  private Long categoryId;

  public BlogQuery() {
  }

  public BlogQuery(String title, Long categoryId, Boolean recommend) {
    this.title = title;
    this.categoryId = categoryId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }
}

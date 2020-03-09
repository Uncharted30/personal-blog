package com.pan.blog.entities.dto;

import java.util.Date;

public class BlogListDto {
  private Long id;
  private String title;
  private Date createDate;
  private String content;
  private Boolean draft;
  private Integer views;

  public BlogListDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Boolean getDraft() {
    return draft;
  }

  public void setDraft(Boolean draft) {
    this.draft = draft;
  }

  public Integer getViews() {
    return views;
  }

  public void setViews(Integer views) {
    this.views = views;
  }
}

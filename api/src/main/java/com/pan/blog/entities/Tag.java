package com.pan.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_tag")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @JsonIgnore
  @ManyToMany(targetEntity = Blog.class)
  @JoinTable(name = "t_blog_tags", joinColumns = {
      @JoinColumn(name = "tag_id", referencedColumnName = "id")}, inverseJoinColumns = {
      @JoinColumn(name = "blog_id", referencedColumnName = "id")})
  private List<Blog> blogList = new ArrayList<>();

  public Tag() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Blog> getBlogList() {
    return blogList;
  }

  public void setBlogList(List<Blog> blogList) {
    this.blogList = blogList;
  }

  @Override
  public String toString() {
    return "Tag{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}

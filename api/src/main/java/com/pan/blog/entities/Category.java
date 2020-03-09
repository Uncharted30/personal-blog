package com.pan.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "t_category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank(message = "Category name cannot be empty")
  private String name;

  @JsonIgnore
  @OneToMany(mappedBy = "category")
  private List<Blog> blogList = new ArrayList<>();

  public Category() {
  }

  public Category(String name) {
    this.name = name;
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
    return "Category{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}

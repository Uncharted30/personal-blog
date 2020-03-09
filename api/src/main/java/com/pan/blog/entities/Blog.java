package com.pan.blog.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Represents a blog with its details.
 */
@Entity
@Table(name = "t_blog")
@EntityListeners(AuditingEntityListener.class)
public class Blog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank
  private String title;

  @Basic(fetch = FetchType.LAZY)
  @Lob
  @NotBlank
  private String content;
  @Basic(fetch = FetchType.LAZY)
  @Lob
  @NotBlank
  private String contentMd;
  private String headerImgUrl;
  private Integer views;
  private Boolean draft;
  private Boolean original;
  private String author;
  private String originalUrl;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss", timezone = "GMT-8")
  @CreatedDate
  private Date createTime;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
  @LastModifiedDate
  private Date updateTime;

  @ManyToOne(targetEntity = Category.class)
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private Category category;

  @ManyToMany(targetEntity = Tag.class, fetch = FetchType.EAGER)
  @JoinTable(name = "t_blog_tags", joinColumns = {
      @JoinColumn(name = "blog_id", referencedColumnName = "id")}, inverseJoinColumns = {
      @JoinColumn(name = "tag_id", referencedColumnName = "id")})
  private List<Tag> tags;

  @JsonIgnore
  @OneToMany(mappedBy = "blog")
  private List<Comment> comments = new ArrayList<>();

  public Blog() {
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getHeaderImgUrl() {
    return headerImgUrl;
  }

  public void setHeaderImgUrl(String headerImgUrl) {
    this.headerImgUrl = headerImgUrl;
  }

  public Boolean getDraft() {
    return draft;
  }

  public void setDraft(Boolean draft) {
    this.draft = draft;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public Integer getViews() {
    return views;
  }

  public void setViews(Integer views) {
    this.views = views;
  }

  public Boolean getOriginal() {
    return this.original;
  }

  public void setOriginal(Boolean original) {
    this.original = original;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getOriginalUrl() {
    return originalUrl;
  }

  public void setOriginalUrl(String originalUrl) {
    this.originalUrl = originalUrl;
  }

  public String getContentMd() {
    return contentMd;
  }

  public void setContentMd(String contentMd) {
    this.contentMd = contentMd;
  }

  @Override
  public String toString() {
    return "Blog{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", headerImgURL='" + headerImgUrl + '\'' +
        ", view=" + views +
        ", isDraft=" + draft +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", category=" + category +
        ", tags=" + tags +
        ", comments=" + comments +
        '}';
  }
}

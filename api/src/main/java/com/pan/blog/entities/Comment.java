package com.pan.blog.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_comment")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nickname;
  private String email;
  private String content;
  private String ip;
  private String device;
  private String location;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss", timezone = "GMT-8")
  private Date createTime;

  @ManyToOne
  @JoinColumn(name = "blog_id", referencedColumnName = "id")
  @JsonIgnoreProperties({"content", "contentMd", "author", "createTime", "updateTime", "category",
      "tags", "comments", "views", "original", "draft"})
  private Blog blog;

  @OneToMany(mappedBy = "parentComment", fetch = FetchType.EAGER)
  private List<Comment> subComments = new ArrayList<>();

  @JsonIgnoreProperties({"email", "content", "ip", "device", "location", "createTime", "blog", "subComments"})
  @ManyToOne
  @JoinColumn(name = "parent_comment_id", referencedColumnName = "id")
  private Comment parentComment;

  public Comment() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Blog getBlog() {
    return blog;
  }

  public void setBlog(Blog blog) {
    this.blog = blog;
  }

  public List<Comment> getSubComments() {
    return subComments;
  }

  public void setSubComments(List<Comment> subComments) {
    this.subComments = subComments;
  }

  public Comment getParentComment() {
    return parentComment;
  }

  public void setParentComment(Comment parentComment) {
    this.parentComment = parentComment;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }


  @Override
  public String toString() {
    return "Comment{" +
        "id=" + id +
        ", nickname='" + nickname + '\'' +
        ", email='" + email + '\'' +
        ", content='" + content + '\'' +
        ", ip='" + ip + '\'' +
        ", device='" + device + '\'' +
        ", createTime=" + createTime +
        ", blog=" + blog +
        ", subComments=" + subComments +
        ", parentComment=" + parentComment +
        '}';
  }
}

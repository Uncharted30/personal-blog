package com.pan.blog.entities.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class GeneralStatsDto {

  private Long todayVisits;
  private Long posts;
  private Long comments;
  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss", timezone = "GMT-8")
  private Date lastLogin;
  private String nickname;
  private String email;
  private String version = "V1.0.1";
  private String avatarUrl;

  public GeneralStatsDto() {
  }

  public GeneralStatsDto(Long todayVisits, Long posts, Long comments, Date lastLogin,
      String nickname, String email, String avatarUrl) {
    this.todayVisits = todayVisits;
    this.posts = posts;
    this.comments = comments;
    this.lastLogin = lastLogin;
    this.nickname = nickname;
    this.email = email;
    this.avatarUrl = avatarUrl;
  }

  public Long getTodayVisits() {
    return todayVisits;
  }

  public void setTodayVisits(Long todayVisits) {
    this.todayVisits = todayVisits;
  }

  public Long getPosts() {
    return posts;
  }

  public void setPosts(Long posts) {
    this.posts = posts;
  }

  public Long getComments() {
    return comments;
  }

  public void setComments(Long comments) {
    this.comments = comments;
  }

  public Date getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
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

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  @Override
  public String toString() {
    return "GeneralStatsDto{" +
        "todayVisits=" + todayVisits +
        ", posts=" + posts +
        ", comments=" + comments +
        ", lastLogin=" + lastLogin +
        ", nickname='" + nickname + '\'' +
        ", email='" + email + '\'' +
        ", version='" + version + '\'' +
        ", avatarUrl='" + avatarUrl + '\'' +
        '}';
  }
}

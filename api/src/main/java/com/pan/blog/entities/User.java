package com.pan.blog.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "t_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nickname;
  private String username;
  private String password;
  private String email;
  private String avatarUrl;
  @Basic(fetch = FetchType.LAZY)
  @Lob
  @NotBlank
  private String bio;
  @Basic(fetch = FetchType.LAZY)
  @Lob
  @NotBlank
  private String bioMd;

  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss", timezone = "GMT-8")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastLogin;

  @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss", timezone = "GMT-8")
  @Temporal(TemporalType.TIMESTAMP)
  private Date bioEditTime;

  public User() {
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public Date getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getBioMd() {
    return bioMd;
  }

  public void setBioMd(String bioMd) {
    this.bioMd = bioMd;
  }

  public Date getBioEditTime() {
    return bioEditTime;
  }

  public void setBioEditTime(Date bioEditTime) {
    this.bioEditTime = bioEditTime;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", nickname='" + nickname + '\'' +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", email='" + email + '\'' +
        ", avatarUrl='" + avatarUrl + '\'' +
        ", bio='" + bio + '\'' +
        ", bioMd='" + bioMd + '\'' +
        ", lastLogin=" + lastLogin +
        ", bioEditTime=" + bioEditTime +
        '}';
  }
}

package com.pan.blog.vo;

import com.pan.blog.utils.IP2Location;

public class CommentQuery {

  String nickname;
  String content;

  public CommentQuery() {
  }

  public CommentQuery(String nickname, String content) {
    this.nickname = nickname;
    this.content = content;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}

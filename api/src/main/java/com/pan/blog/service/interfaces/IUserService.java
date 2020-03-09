package com.pan.blog.service.interfaces;

import com.pan.blog.entities.User;

public interface IUserService {
  User findUserByUsername(String username);

  void updateUser(User user);

  User getUser();
}

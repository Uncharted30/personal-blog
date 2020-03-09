package com.pan.blog.service;

import com.pan.blog.dao.UserRepository;
import com.pan.blog.entities.User;
import com.pan.blog.service.interfaces.IUserService;
import java.util.Date;
import javax.persistence.EntityNotFoundException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService implements IUserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User findUserByUsername(String username) {
    return userRepository.findUserByUsername(username);
  }

  @Override
  public void updateUser(User user) {
    User oldUser = this.userRepository.findById(user.getId()).orElse(null);
    if (oldUser == null) {
      throw new EntityNotFoundException("User not found.");
    }
    if (!StringUtils.isEmpty(user.getAvatarUrl())) {
      oldUser.setAvatarUrl(user.getAvatarUrl());
    }
    if (!StringUtils.isEmpty(user.getNickname())) {
      oldUser.setNickname(user.getNickname());
    }
    if (!StringUtils.isEmpty(user.getEmail())) {
      oldUser.setEmail(user.getEmail());
    }
    if (!user.getBio().equals(oldUser.getBio())) {
      oldUser.setBio(user.getBio());
      oldUser.setBioMd(user.getBioMd());
      oldUser.setBioEditTime(new Date());
    }
    if (user.getPassword() != null) {
      oldUser.setPassword(
          new SimpleHash("MD5", user.getPassword(), user.getUsername(), 1024).toString());
    }
    this.userRepository.save(oldUser);
  }

  @Override
  public User getUser() {
    User user = this.userRepository.findAll().get(0);
    user.setPassword(null);
    return user;
  }


}

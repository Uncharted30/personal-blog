package com.pan.blog.controller;

import com.pan.blog.entities.User;
import com.pan.blog.service.UserService;
import com.pan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @PutMapping("/admin/user")
  public Result updateUser(@RequestBody User user) {
    this.userService.updateUser(user);
    return new Result();
  }

  @GetMapping("/user")
  public Result getUserInfo() {
    return new Result(this.userService.getUser());
  }
}

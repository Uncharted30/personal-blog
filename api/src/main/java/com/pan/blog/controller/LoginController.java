package com.pan.blog.controller;

import com.pan.blog.annotation.OperationLog;
import com.pan.blog.constants.enums.StatusCode;
import com.pan.blog.entities.User;
import com.pan.blog.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.pan.blog.utils.Result;

@RestController
public class LoginController {

  @Autowired
  private UserService userService;

  @PostMapping("/admin/login")
  @OperationLog("Login")
  public Result login(@RequestBody User loginUser) {
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginUser.getUsername(),
        loginUser.getPassword());
    subject.login(usernamePasswordToken);
    Map<String, Object> map = new HashMap<>();
    map.put("token", subject.getSession().getId());
    return new Result(map);
  }

  @GetMapping(value = "/admin/logout")
  public Result logout() {
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    return new Result();
  }

  @RequestMapping("/unauthorized")
  public Result unauth() {
    return new Result(HttpStatus.UNAUTHORIZED);
  }
}

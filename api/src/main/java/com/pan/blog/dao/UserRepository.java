package com.pan.blog.dao;

import com.pan.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findUserByUsername(String username);
}

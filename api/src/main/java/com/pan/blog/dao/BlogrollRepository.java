package com.pan.blog.dao;

import com.pan.blog.entities.Blogroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlogrollRepository extends JpaRepository<Blogroll, Long>,
    JpaSpecificationExecutor<Blogroll> {
}

package com.pan.blog.dao;

import com.pan.blog.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {

  default Tag findByIdOrNull(Long id) {
    return findById(id).orElse(null);
  }
}

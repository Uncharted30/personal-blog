package com.pan.blog.dao;

import com.pan.blog.entities.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {

}

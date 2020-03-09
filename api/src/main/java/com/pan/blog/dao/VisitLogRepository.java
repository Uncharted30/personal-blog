package com.pan.blog.dao;

import com.pan.blog.entities.VisitLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VisitLogRepository extends JpaRepository<VisitLog, Long>,
    JpaSpecificationExecutor<VisitLog> {

  List<VisitLog> findByIp(String ip);
}

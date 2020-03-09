package com.pan.blog.service.interfaces;

import com.pan.blog.entities.VisitLog;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IVisitLogService {

  void addVisitLog(String ip);

  Page<VisitLog> listVisitLog(Integer pageSize, Integer page, Date startDate, Date endDate);

  void deleteVisitLog(Long id);
}

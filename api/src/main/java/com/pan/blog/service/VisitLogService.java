package com.pan.blog.service;


import com.pan.blog.dao.VisitLogRepository;
import com.pan.blog.entities.VisitLog;
import com.pan.blog.exceptions.CommonException;
import com.pan.blog.service.interfaces.IVisitLogService;
import com.pan.blog.utils.IP2Location;
import com.pan.blog.utils.PageUtil;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class VisitLogService implements IVisitLogService {

  @Autowired
  private VisitLogRepository visitLogRepository;

  @Override
  public void addVisitLog(String ip) {

    String location = null;

    List<VisitLog> visitLogs = this.visitLogRepository.findByIp(ip);

    VisitLog visitLog = null;

    if (visitLogs != null && visitLogs.size() > 0) {
      visitLog = visitLogs.get(0);
      visitLog.setTime(new Date());
    } else {
      visitLog = new VisitLog();
      visitLog.setTime(new Date());
      visitLog.setIp(ip);
      try {
        location = IP2Location.convert(ip);
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }
      visitLog.setLocation(location);
    }
    this.visitLogRepository.save(visitLog);
  }

  @Override
  public Page<VisitLog> listVisitLog(Integer pageSize, Integer page, Date startDate, Date endDate) {
    PageUtil.validPage(pageSize, page, this.visitLogRepository);
    Sort sort = Sort.by(Direction.DESC, "time");
    Pageable pageable = PageRequest.of(page, pageSize, sort);
    if (startDate != null || endDate != null) {
      return this.visitLogRepository.findAll(new Specification<VisitLog>() {
        @Override
        public Predicate toPredicate(Root<VisitLog> root, CriteriaQuery<?> criteriaQuery,
            CriteriaBuilder criteriaBuilder) {
          Predicate predicate1 = null;
          Predicate predicate2 = null;
          if (startDate != null) {
            predicate1 = criteriaBuilder.greaterThanOrEqualTo(root.get("time").as(Date.class), startDate);
          }
          if (endDate != null) {
            predicate2 = criteriaBuilder.lessThanOrEqualTo(root.get("time").as(Date.class), endDate);
          }
          if (predicate1 != null && predicate2 != null) {
            return criteriaBuilder.and(predicate1, predicate2);
          }
          return predicate1 == null ? predicate2 : predicate1;
        }
      }, pageable);
    }
    return this.visitLogRepository.findAll(pageable);
  }

  @Override
  public void deleteVisitLog(Long id) {
    this.visitLogRepository.deleteById(id);
  }
}

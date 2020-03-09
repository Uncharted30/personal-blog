package com.pan.blog.service;

import com.pan.blog.dao.BlogRepository;
import com.pan.blog.dao.CommentRepository;
import com.pan.blog.dao.UserRepository;
import com.pan.blog.dao.VisitLogRepository;
import com.pan.blog.entities.User;
import com.pan.blog.entities.VisitLog;
import com.pan.blog.entities.dto.GeneralStatsDto;
import com.pan.blog.service.interfaces.IStatsService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class StatsService implements IStatsService {

  @Autowired
  private BlogRepository blogRepository;

  @Autowired
  private VisitLogRepository visitLogRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Override
  public GeneralStatsDto getGeneralStats() {
    Long todayVisits = this.visitLogRepository.count(new Specification<VisitLog>() {
      @Override
      public Predicate toPredicate(Root<VisitLog> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {
        Calendar detailedToday = Calendar.getInstance();
        detailedToday.setTime(new Date());
        Calendar today = Calendar.getInstance();
        today.set(detailedToday.get(Calendar.YEAR), detailedToday.get(Calendar.MONTH),
            detailedToday.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return criteriaBuilder.greaterThanOrEqualTo(root.get("time"), today.getTime());
      }
    });

    Long posts = this.blogRepository.count();
    Long comments = this.commentRepository.count();
    List<User> users = this.userRepository.findAll();
    if (users.size() == 0) {
      throw new EntityNotFoundException("No user exists.");
    }
    User user = users.get(0);
    Date lastLogin = user.getLastLogin();
    String email = user.getEmail();
    String nickname = user.getNickname();
    String avatarUrl = user.getAvatarUrl();
    return new GeneralStatsDto(todayVisits, posts, comments, lastLogin, nickname, email, avatarUrl);
  }
}

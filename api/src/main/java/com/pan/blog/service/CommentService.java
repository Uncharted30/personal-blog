package com.pan.blog.service;

import com.pan.blog.dao.CommentRepository;
import com.pan.blog.entities.Comment;
import com.pan.blog.exceptions.PageOutOfBoundException;
import com.pan.blog.service.interfaces.ICommentService;
import com.pan.blog.utils.IP2Location;
import com.pan.blog.utils.IPUtil;
import com.pan.blog.utils.PageUtil;
import com.pan.blog.vo.CommentQuery;
import eu.bitwalker.useragentutils.UserAgent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class CommentService implements ICommentService {

  @Autowired
  private CommentRepository commentRepository;

  @Override
  public Page<Comment> listComment(Integer pageSize, Integer page, CommentQuery commentQuery) {
    PageUtil.validPage(pageSize, page, this.commentRepository);
    Sort sort = Sort.by(Direction.DESC, "createTime");
    Pageable pageable = PageRequest.of(page, pageSize, sort);
    if (commentQuery != null && (!StringUtils.isEmpty(commentQuery.getContent()) || !StringUtils
        .isEmpty(commentQuery.getNickname()))) {
      return this.commentRepository.findAll(new Specification<Comment>() {
        @Override
        public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> criteriaQuery,
            CriteriaBuilder criteriaBuilder) {
          Predicate predicate1 = null;
          Predicate predicate2 = null;
          if (!StringUtils.isEmpty(commentQuery.getContent())) {
            predicate1 = criteriaBuilder.like(root.get("content").as(String.class), "%" + commentQuery.getContent() + "%");
          }
          if (!StringUtils.isEmpty(commentQuery.getNickname())) {
            predicate2 = criteriaBuilder
                .like(root.get("nickname").as(String.class), "%" + commentQuery.getNickname() + "%");
          }
          if (predicate1 != null && predicate2 != null) {
            return criteriaBuilder.and(predicate1, predicate2);
          }
          return predicate1 == null ? predicate2 : predicate1;
        }
      }, pageable);
    }

    return this.commentRepository.findAll(pageable);
  }

  @Override
  public void deleteComment(Long id) {
    this.commentRepository.deleteById(id);
  }

  @Override
  public void addComment(Comment comment) throws Exception {
    comment.setCreateTime(new Date());
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    if (attributes != null) {
      HttpServletRequest request = attributes.getRequest();
      String ip = IPUtil.getIp(request);
      comment.setIp(ip);
      String header = request.getHeader("User-Agent");
      UserAgent userAgent = UserAgent.parseUserAgentString(header);
      String device = userAgent.getBrowser().getName() + userAgent.getOperatingSystem();
      comment.setDevice(device);
      try {
        comment.setLocation(IP2Location.convert(comment.getIp()));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    this.commentRepository.save(comment);
  }

  @Override
  public List<Comment> getBlogComments(Long blogId) {
    return this.commentRepository.findAll(new Specification<Comment>() {
      @Override
      public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {
        Predicate predicate1 = criteriaBuilder.isNull(root.get("parentComment"));
        Predicate predicate2 = criteriaBuilder.equal(root.get("blog").get("id"), blogId);
        return criteriaBuilder.and(predicate1, predicate2);
      }
    });
  }
}

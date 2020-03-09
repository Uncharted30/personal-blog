package com.pan.blog.service.interfaces;

import com.pan.blog.entities.Comment;
import com.pan.blog.vo.CommentQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommentService {

  Page<Comment> listComment(Integer pageSize, Integer page, CommentQuery commentQuery);

  void deleteComment(Long id);

  void addComment(Comment comment) throws Exception;

  List<Comment> getBlogComments(Long blogId);
}

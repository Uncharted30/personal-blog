package com.pan.blog.controller;

import com.pan.blog.annotation.OperationLog;
import com.pan.blog.entities.Comment;
import com.pan.blog.service.CommentService;
import com.pan.blog.utils.Result;
import com.pan.blog.vo.CommentQuery;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

  @Autowired
  private CommentService commentService;

  @GetMapping("/comment/blog/{blogId}")
  public Result getBlogComments(@PathVariable Long blogId) {
    List<Comment> comments = this.commentService.getBlogComments(blogId);
    System.out.println(comments.size());
    return new Result(comments);
  }

  @GetMapping("/comment/list/{pageSize}/{page}")
  public Result findAllComment(@PathVariable Integer pageSize,
      @PathVariable(required = false) Integer page,
      @RequestParam(required = false) String content,
      @RequestParam(required = false) String nickname) {
    page = page == null ? 0 : page;
    System.out.println(content);
    CommentQuery commentQuery = new CommentQuery(nickname, content);
    Page<Comment> commentPage = this.commentService.listComment(pageSize, page, commentQuery);
    return new Result(commentPage);
  }

  @PostMapping("/comment")
  @OperationLog("Add comment")
  public Result addComment(@RequestBody Comment comment)
      throws Exception {
    this.commentService.addComment(comment);
    return new Result();
  }

  @DeleteMapping("/admin/comment/{id}")
  @OperationLog("Delete comment")
  public Result deleteComment(@PathVariable Long id) {
    this.commentService.deleteComment(id);
    return new Result();
  }
}

package com.pan.blog.controller;

import com.pan.blog.annotation.OperationLog;
import com.pan.blog.entities.Tag;
import com.pan.blog.exceptions.PageOutOfBoundException;
import com.pan.blog.service.TagService;
import com.pan.blog.utils.Result;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

  @Autowired
  private TagService tagService;

  @GetMapping("/tags")
  public Result findAll() {
    List<Tag> tags = this.tagService.findAllTags();
    return new Result(tags);
  }

  @GetMapping(value = {"/tags/list/{pageSize}/{page}/{keyword}", "/tags/list/{pageSize}/{page}"})
  public Result listTag(@PathVariable(required = false) Integer page,
      @PathVariable Integer pageSize,
      @PathVariable(required = false) String keyword) {
    page = page == null ? 0 : page;
    Page<Tag> tagPage = this.tagService.listTag(pageSize, page, keyword);
    return new Result(tagPage);
  }

  @PostMapping("/admin/tags")
  @OperationLog("Save tag")
  public Result saveTag(@RequestBody Tag tag) {
    this.tagService.saveTag(tag);
    return new Result();
  }

  @PutMapping("/admin/tags")
  @OperationLog("Update tag")
  public Result updateTag(@RequestBody Tag tag) {
    this.tagService.updateTag(tag);
    return new Result();
  }

  @DeleteMapping("/admin/tags/{id}")
  @OperationLog("Delete tag")
  public Result deleteTag(@PathVariable Long id) {
    this.tagService.deleteTag(id);
    return new Result();
  }

  @GetMapping("/tags/{id}")
  public Result findTagById(@PathVariable Long id) {
    Tag tag = this.tagService.findTagById(id);
    return new Result(tag);
  }
}

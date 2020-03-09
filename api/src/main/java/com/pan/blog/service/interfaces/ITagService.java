package com.pan.blog.service.interfaces;

import com.pan.blog.entities.Tag;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ITagService {

  List<Tag> findAllTags();

  Page<Tag> listTag(Integer pageSize, Integer page, String keyword);

  Tag findTagById(Long id);

  void deleteTag(Long id);

  void updateTag(Tag tag);

  void saveTag(Tag tag);
}

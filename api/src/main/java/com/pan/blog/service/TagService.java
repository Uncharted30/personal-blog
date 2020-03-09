package com.pan.blog.service;

import com.pan.blog.dao.TagRepository;
import com.pan.blog.entities.Tag;
import com.pan.blog.service.interfaces.ITagService;
import com.pan.blog.utils.PageUtil;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagService implements ITagService {

  @Autowired
  private TagRepository tagRepository;

  @Override
  public List<Tag> findAllTags() {
    return this.tagRepository.findAll();
  }

  @Override
  public Page<Tag> listTag(Integer pageSize, Integer page, String keyword) {
    PageUtil.validPage(pageSize, page, this.tagRepository);
    Sort sort = Sort.by(Direction.DESC, "id");
    Pageable pageable = PageRequest.of(page, pageSize, sort);
    if (keyword != null && !keyword.isEmpty()) {
      return this.tagRepository.findAll(new Specification<Tag>() {
        @Override
        public Predicate toPredicate(Root<Tag> root, CriteriaQuery<?> criteriaQuery,
            CriteriaBuilder criteriaBuilder) {
          return criteriaBuilder.like(root.get("name").as(String.class), "%" + keyword + "%");
        }
      }, pageable);
    }
    return this.tagRepository.findAll(pageable);
  }

  @Override
  public Tag findTagById(Long id) {
    Tag tag = this.tagRepository.findByIdOrNull(id);
    if (tag == null) {
      throw new EntityNotFoundException("Cannot find the tag");
    }
    return tag;
  }

  @Override
  @Transactional
  public void deleteTag(Long id) {
    this.tagRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void updateTag(Tag tag) {
    Tag oldTag = this.tagRepository.findByIdOrNull(tag.getId());
    if (oldTag == null) {
      throw new EntityNotFoundException("Cannot find the tag");
    }
    BeanUtils.copyProperties(tag, oldTag);
    this.tagRepository.save(oldTag);
  }

  @Override
  @Transactional
  public void saveTag(Tag tag) {
    this.tagRepository.save(tag);
  }
}

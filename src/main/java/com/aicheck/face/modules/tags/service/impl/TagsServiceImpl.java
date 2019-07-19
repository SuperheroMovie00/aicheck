/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.tags.service.impl;

import com.aicheck.face.modules.tags.entity.Tags;
import com.aicheck.face.modules.tags.repository.TagsRepository;
import com.aicheck.face.modules.tags.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:42 PM 2018/11/26
 */
@Service("tagsService")
public class TagsServiceImpl implements TagsService {
    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public Tags save(Tags tags) {
        return tagsRepository.save(tags);
    }

    @Override
    public List<Tags> save(List<Tags> t) {
        return tagsRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        tagsRepository.deleteById(integer);
    }

    @Override
    public Tags update(Tags tags) {
        return tagsRepository.save(tags);
    }

    @Override
    public List<Tags> find(Tags tags) {
        return null;
    }

    @Override
    public List<Tags> findAll() {
        return tagsRepository.findAll();
    }

    @Override
    public Tags findById(Integer integer) {

        Optional<Tags> optionalTags = tagsRepository.findById(integer);

        return optionalTags.isPresent()?optionalTags.get():null;
    }

    @Override
    public Page<Tags> findAllList(Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage -1,pageSize);
        return tagsRepository.findAll(pageable);
    }

    @Override
    public List<Tags> findByIdIn(List<Integer> idList) {
        return tagsRepository.findByIdIn(idList);
    }

	@Override
	public List<Tags> querytagsforcustomerid(Integer customerid) {
		return tagsRepository.querytagsforcustomerid(customerid);
	}
}

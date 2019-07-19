package com.aicheck.face.modules.levels.service;

import org.springframework.data.domain.Page;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.levels.entity.Levels;

public interface LevelsService extends BaseService<Levels, Integer> {
	Page<Levels> findAllList(Integer currentPage, Integer pageSize);
}
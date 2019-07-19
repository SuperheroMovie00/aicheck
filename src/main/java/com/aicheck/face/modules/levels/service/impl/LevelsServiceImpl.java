package com.aicheck.face.modules.levels.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aicheck.face.modules.levels.entity.Levels;
import com.aicheck.face.modules.levels.repository.LevelsRepository;
import com.aicheck.face.modules.levels.service.LevelsService;
@Service("LevelsService")
public class LevelsServiceImpl implements LevelsService {
	@Autowired
	private LevelsRepository levelsRepository;
	
	@Override
	public Levels save(Levels level) {
		return levelsRepository.save(level);
	}

	@Override
	public List<Levels> save(List<Levels> t) {
		return levelsRepository.saveAll(t);
	}

	@Override
	public void delete(Integer id) {
		levelsRepository.deleteById(id);
		
	}

	@Override
	public Levels update(Levels t) {
		// TODO Auto-generated method stub
		return levelsRepository.save(t);
	}

	@Override
	public List<Levels> find(Levels t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Levels> findAll() {
		// TODO Auto-generated method stub
		return levelsRepository.findAll();
	}

	@Override
	public Levels findById(Integer id) {
		// TODO Auto-generated method stub
		Optional<Levels> optionalLevel = levelsRepository.findById(id);
		
		return optionalLevel.isPresent() ? optionalLevel.get():null;
	}

	@Override
	public Page<Levels> findAllList(Integer currentPage, Integer pageSize) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(currentPage-1, pageSize);
		return levelsRepository.findAll(pageable);
	}
	
}
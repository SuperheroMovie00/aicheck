package com.aicheck.face.modules.device.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aicheck.face.modules.device.entity.relevance;
import com.aicheck.face.modules.device.repository.relevanceRepository;
import com.aicheck.face.modules.device.service.relevanceService;

@Service("relevanceService")
public class relevanceServiceImpl implements relevanceService {
	
	@Autowired
    private relevanceRepository relevancerepository;

	@Override
	public relevance save(relevance t) {
		// TODO Auto-generated method stub
		return relevancerepository.save(t);
	}

	@Override
	public List<relevance> save(List<relevance> t) {
		// TODO Auto-generated method stub
		return relevancerepository.saveAll(t);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		relevancerepository.deleteById(id);
	}

	@Override
	public relevance update(relevance t) {
		// TODO Auto-generated method stub
		return relevancerepository.save(t);
	}

	@Override
	public List<relevance> find(relevance t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<relevance> findAll() {
		// TODO Auto-generated method stub
		return relevancerepository.findAll();
	}

	@Override
	public relevance findById(Integer id) {
		Optional<relevance> optional = relevancerepository.findById(id);
        return optional.isPresent()?optional.get():null;
	}

	@Override
	public relevance queryrelevanceforboxdeviceid(Integer id) {
		// TODO Auto-generated method stub
		return relevancerepository.queryrelevanceforboxdeviceid(id);
	}

}

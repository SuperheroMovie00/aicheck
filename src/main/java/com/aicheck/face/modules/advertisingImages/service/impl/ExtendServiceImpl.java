package com.aicheck.face.modules.advertisingImages.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aicheck.face.modules.advertisingImages.entity.Extend;
import com.aicheck.face.modules.advertisingImages.repository.extendRepository;
import com.aicheck.face.modules.advertisingImages.service.ExtendService;

@Service
public class ExtendServiceImpl implements ExtendService {

	 @Autowired
	 private extendRepository extendrepository;
	
	@Override
	public Extend save(Extend t) {
		return extendrepository.save(t);
	}

	@Override
	public List<Extend> save(List<Extend> t) {
		// TODO Auto-generated method stub
		return extendrepository.saveAll(t);
	}

	@Override
	public void delete(Integer id) {
		extendrepository.deleteById(id);

	}

	@Override
	public Extend update(Extend t) {
		// TODO Auto-generated method stub
		return extendrepository.save(t);
	}

	@Override
	public List<Extend> find(Extend t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Extend> findAll() {
		// TODO Auto-generated method stub
		return extendrepository.findAll();
	}

	@Override
	public Extend findById(Integer id) {
		Optional<Extend> optional = extendrepository.findById(id);
        return optional.isPresent()?optional.get():null;
	}

}

package com.aicheck.face.modules.sync.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.aicheck.face.modules.sync.entity.sync;
import com.aicheck.face.modules.sync.repository.syncRepository;
import com.aicheck.face.modules.sync.service.syncService;

@Service
public class syncServiceImpl implements syncService {
	
	@Autowired
    private syncRepository syncrepository;

	@Override
	public sync save(sync t) {
		// TODO Auto-generated method stub
		return syncrepository.save(t);
	}

	@Override
	public List<sync> save(List<sync> t) {
		// TODO Auto-generated method stub
		return syncrepository.saveAll(t);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		syncrepository.deleteById(id);
	}

	@Override
	public sync update(sync t) {
		// TODO Auto-generated method stub
		return syncrepository.save(t);
	}

	@Override
	public List<sync> find(sync t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<sync> findAll() {
		// TODO Auto-generated method stub
		return syncrepository.findAll();
	}

	@Override
	public sync findById(Integer id) {
		// TODO Auto-generated method stub
		Optional<sync> optionalCustomer = syncrepository.findById(id);
        return optionalCustomer.isPresent()?optionalCustomer.get():null;
	}

	
	@Override
	public List<sync> querysynclist(Integer status,String func) {
		// TODO Auto-generated method stub
		return syncrepository.querysynclist(status,func);
	}

}

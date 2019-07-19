package com.aicheck.face.modules.advertisingImages.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aicheck.face.modules.advertisingImages.entity.ImageStrategy;
import com.aicheck.face.modules.advertisingImages.repository.ImageStrategyRepository;
import com.aicheck.face.modules.advertisingImages.service.ImageStrategyService;
@Service
public class ImageStrategyServiceImpl implements ImageStrategyService {
	 @Autowired
	 private ImageStrategyRepository imagestrategyrepository;

	@Override
	public ImageStrategy save(ImageStrategy t) {
		// TODO Auto-generated method stub
		return imagestrategyrepository.save(t);
	}

	@Override
	public List<ImageStrategy> save(List<ImageStrategy> t) {
		// TODO Auto-generated method stub
		return imagestrategyrepository.saveAll(t);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		imagestrategyrepository.deleteById(id);
	}

	@Override
	public ImageStrategy update(ImageStrategy t) {
		// TODO Auto-generated method stub
		return imagestrategyrepository.save(t);
	}

	@Override
	public List<ImageStrategy> find(ImageStrategy t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImageStrategy> findAll() {
		// TODO Auto-generated method stub
		return imagestrategyrepository.findAll();
	}

	@Override
	public ImageStrategy findById(Integer id) {
		// TODO Auto-generated method stub
		Optional<ImageStrategy> optional = imagestrategyrepository.findById(id);
		 return optional.isPresent()?optional.get():null;
	}

	@Override
	public List<ImageStrategy> querystrategyforcusid(Integer groupid) {
		// TODO Auto-generated method stub
		return imagestrategyrepository.querystrategyforcusid(groupid);
	}


}

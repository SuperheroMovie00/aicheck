package com.aicheck.face.modules.advertisingImages.service;

import java.util.List;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.advertisingImages.entity.ImageStrategy;

public interface ImageStrategyService extends BaseService<ImageStrategy,Integer> {

	List<ImageStrategy> querystrategyforcusid(Integer groupid);
	
}

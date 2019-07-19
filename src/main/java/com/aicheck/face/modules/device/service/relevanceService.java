package com.aicheck.face.modules.device.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.device.entity.relevance;

public interface relevanceService extends BaseService<relevance,Integer> {

	relevance queryrelevanceforboxdeviceid(Integer id);

	
}

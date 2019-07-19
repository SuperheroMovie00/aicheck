package com.aicheck.face.modules.sync.service;

import java.util.List;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.sync.entity.sync;

public interface syncService extends BaseService<sync,Integer> {

	List<sync> querysynclist(Integer status,String func);
	
}

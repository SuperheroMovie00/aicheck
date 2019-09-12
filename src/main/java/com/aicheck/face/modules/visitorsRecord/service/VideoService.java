package com.aicheck.face.modules.visitorsRecord.service;

import com.aicheck.face.base.BaseService;

import com.aicheck.face.modules.visitorsRecord.entity.Video;

public interface VideoService  extends BaseService<Video,Integer> {

	Video findvideo(String date);
	
}

package com.aicheck.face.modules.visitorsRecord.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aicheck.face.modules.visitorsRecord.entity.Carstatistics;
import com.aicheck.face.modules.visitorsRecord.entity.Video;
import com.aicheck.face.modules.visitorsRecord.repository.VideoRepository;
import com.aicheck.face.modules.visitorsRecord.repository.VideostatisticRepository;
import com.aicheck.face.modules.visitorsRecord.service.VideoService;

@Service("VideoService")
public class VideoServiceimpl implements VideoService {

	@Autowired
	private VideoRepository videorepository;
	
	@Override
	public Video save(Video t) {
		// TODO Auto-generated method stub
		return videorepository.save(t);
	}

	@Override
	public List<Video> save(List<Video> t) {
		// TODO Auto-generated method stub
		return videorepository.saveAll(t);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		videorepository.deleteById(id);
	}

	@Override
	public Video update(Video t) {
		// TODO Auto-generated method stub
		return videorepository.save(t);
	}

	@Override
	public List<Video> find(Video t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Video> findAll() {
		// TODO Auto-generated method stub
		return videorepository.findAll();
	}

	@Override
	public Video findById(Integer id) {
		Optional<Video> optionalVisitorsRecord = videorepository.findById(id);
        return optionalVisitorsRecord.isPresent() ? optionalVisitorsRecord.get() : null;
	}

	@Override
	public Video findvideo(String date) {
		// TODO Auto-generated method stub
		return videorepository.findvideo(date);
	}

}

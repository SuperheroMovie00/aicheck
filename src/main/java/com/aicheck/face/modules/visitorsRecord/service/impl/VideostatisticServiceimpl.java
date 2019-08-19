package com.aicheck.face.modules.visitorsRecord.service.impl;

import com.aicheck.face.modules.visitorsRecord.entity.Videostatistic;

import com.aicheck.face.modules.visitorsRecord.repository.VideostatisticRepository;
import com.aicheck.face.modules.visitorsRecord.service.VideostatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("videostatisticService")
public class VideostatisticServiceimpl implements VideostatisticService {

    @Autowired
    private VideostatisticRepository videostatisticRepository;


    @Override
    public Videostatistic save(Videostatistic videostatistic) {
        return videostatisticRepository.save(videostatistic);
    }

    @Override
    public List<Videostatistic> save(List<Videostatistic> t) {
        return videostatisticRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        videostatisticRepository.deleteById(integer);
    }

    @Override
    public Videostatistic update(Videostatistic videostatistic) {
        return videostatisticRepository.save(videostatistic);
    }

    @Override
    public List<Videostatistic> find(Videostatistic videostatistic) {
        return null;
    }

    @Override
    public List<Videostatistic> findAll() {
        return videostatisticRepository.findAll();
    }

    @Override
    public Videostatistic findById(Integer integer) {
        Optional<Videostatistic> optionalVisitorsRecord = videostatisticRepository.findById(integer);
        return optionalVisitorsRecord.isPresent() ? optionalVisitorsRecord.get() : null;
    }

    @Override
    public Videostatistic findvideostatisicfortype(String type) {
        return videostatisticRepository.findvideostatisicfortype(type);
    }

    @Override
    public List<Videostatistic> findvideostatisicfordaylist(String type) {
        return videostatisticRepository.findvideostatisicfordaylist(type);
    }

	@Override
	public List<Videostatistic> findvideostatisicforweeklist(String datestr) {
		// TODO Auto-generated method stub
		return videostatisticRepository.findvideostatisicforweeklist(datestr);
	}

	@Override
	public List<Videostatistic> findvideostatisicforyearlist(String datestr) {
		// TODO Auto-generated method stub
		return videostatisticRepository.findvideostatisicforyearlist(datestr);
	}

	@Override
	public Videostatistic findvideostatisicforweekyanlist(String datestr) {
		// TODO Auto-generated method stub
		return videostatisticRepository.findvideostatisicforweekyanlist(datestr);
	}

    @Override
    public int  deletevideostatistic(String datestr, String type) {
        return videostatisticRepository.deletevideostatistic(datestr,type);
    }
}

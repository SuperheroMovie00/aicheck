package com.aicheck.face.modules.visitorsRecord.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.visitorsRecord.entity.Videostatistic;

import java.util.List;


public interface VideostatisticService extends BaseService<Videostatistic,Integer> {

    Videostatistic findvideostatisicfortype(String type);

    Videostatistic findvideostatisicfordate(String date);
    
    List<Videostatistic> findvideostatisicfordaylist(String type);
    
    
    
    List<Videostatistic> findvideostatisicforweeklist(String datestr);
    
    List<Videostatistic> findvideostatisicforyearlist(String datestr);
    
    Videostatistic findvideostatisicforweekyanlist(String datestr);

    int deletevideostatistic(String datestr,String type);
}

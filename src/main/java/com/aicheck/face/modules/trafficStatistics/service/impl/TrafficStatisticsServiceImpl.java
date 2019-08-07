/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.trafficStatistics.service.impl;


import com.sun.jna.ptr.IntByReference;
import com.aicheck.face.common.utils.PropertiesUtils;
import com.aicheck.face.modules.i8sdk.dllLibrary.impl.FaceSmartSDKImpl;
import com.aicheck.face.modules.trafficStatistics.entity.TrafficStatistics;
import com.aicheck.face.modules.trafficStatistics.repository.TrafficStatisticsRepository;
import com.aicheck.face.modules.trafficStatistics.service.TrafficStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 7:32 PM 2018/12/26
 */
@Service("trafficStatisticsService")
//@Slf4j
public class TrafficStatisticsServiceImpl implements TrafficStatisticsService {
    @Autowired
    private TrafficStatisticsRepository trafficStatisticsRepository;
    @Override
    public TrafficStatistics save(TrafficStatistics trafficStatistics) {
        return trafficStatisticsRepository.save(trafficStatistics);
    }

    @Override
    public List<TrafficStatistics> save(List<TrafficStatistics> t) {
        return trafficStatisticsRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        trafficStatisticsRepository.deleteById(integer);
    }

    @Override
    public TrafficStatistics update(TrafficStatistics trafficStatistics) {

        return trafficStatisticsRepository.save(trafficStatistics);
    }

    @Override
    public List<TrafficStatistics> find(TrafficStatistics trafficStatistics) {
        return null;
    }

    @Override
    public List<TrafficStatistics> findAll() {
        return trafficStatisticsRepository.findAll();
    }

    @Override
    public TrafficStatistics findById(Integer integer) {
        Optional<TrafficStatistics> optional = trafficStatisticsRepository.findById(integer);
        return optional.isPresent()?optional.get():null;
    }

    @Override
    public int[] getTrafficStatistics() {

        String userDir = System.getProperty("user.dir");

        String pemPath = userDir + "\\src\\main\\resources\\dll\\self.pem";

       // String dllPath = userDir + "\\src\\main\\resources\\dll\\SmartCount.dll";
        String dllPath = userDir + "\\src\\main\\resources\\dll\\I8H_SDK.dll";

        IntByReference intByReference = FaceSmartSDKImpl.SmartCount(dllPath, pemPath, "192.168.1.188", "80", "admin", PropertiesUtils.getInstance().getProperties("cameraPassword"));

        int[] numbers = intByReference.getPointer().getIntArray(0,2);

        return numbers;
    }

}

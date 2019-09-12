package com.aicheck.face.modules.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.face.modules.device.service.DeviceService;
import com.aicheck.face.modules.device.service.relevanceService;
import com.aicheck.face.vo.R;
import com.aicheck.face.modules.device.entity.Device;
import com.aicheck.face.modules.device.entity.relevance;



@RestController
@RequestMapping("/v1/relevance")
//@Slf4j
public class relevanceController {
	
	@Autowired
	private relevanceService  relevanceService;
	@Autowired
	private DeviceService deviceService;

	
	
	@PostMapping("/addrelevance")
	public R addrelevance(relevance relevance) {
		relevance relevancenew=relevanceService.save(relevance);
		Device device=deviceService.findById(relevance.getBoxdeviceId());
		if(device==null){
			return  R.error("/v1/relevance/v1/relevance/addrelevance=>device为空");
		}

		device.setIsRelevance(1);
		deviceService.save(device);
		return  R.ok(relevancenew);		
	}
	
	@PostMapping("/deleterelevanceforid")
	public R deleterelevanceforid(Integer relevanceid) {
		try {
			
			relevance relevance=relevanceService.queryrelevanceforboxdeviceid(relevanceid);
			Device device= deviceService.findById(relevance.getBoxdeviceId());
			if(device==null){
				return  R.error("/v1/relevance/v1/relevance/deleterelevanceforid=>device为空");
			}

			device.setIsRelevance(0);
			deviceService.save(device);
			relevanceService.delete(relevance.getId());
			return R.ok();
			
		} catch (Exception e) {
			// TODO: handle exception
			return R.error();
		}
	}
	
	
}

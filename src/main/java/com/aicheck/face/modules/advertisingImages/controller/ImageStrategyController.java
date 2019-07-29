package com.aicheck.face.modules.advertisingImages.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.face.modules.advertisingImages.entity.ImageStrategy;
import com.aicheck.face.modules.advertisingImages.repository.ImageStrategyRepository;
import com.aicheck.face.modules.advertisingImages.service.ImageStrategyService;
import com.aicheck.face.vo.R;

@RestController
@RequestMapping("/v1/image-strategy")
public class ImageStrategyController {
	@Autowired
	private ImageStrategyService Imagestrategyservice;
	@Autowired 
	private ImageStrategyRepository  imagestrategyrepository;
	
	@PostMapping("/querystrategyforcusid")
	public R querystrategyforcusid(Integer groupid) {
		List<ImageStrategy> imagesteate=Imagestrategyservice.querystrategyforcusid(groupid);

		if (imagesteate==null){
			return  R.error("querystrategyforcusid=> imagesteate为空");
		}

		return R.ok(imagesteate);
	}
	
	
	@PostMapping("/deletestrategy")
	public R deletestrategy(Integer id) {
		try {
			imagestrategyrepository.deleteById(id);
			return R.ok();
		} catch (Exception e) {
			return R.error();
		}
	}
	
	@PostMapping("/findbyid")
	public R findbyidimagestrate(Integer id) {
		Optional<ImageStrategy> img=imagestrategyrepository.findById(id);
		if (img==null){
			return  R.error("/findbyid=> img为空");
		}
		return R.ok(img);
	}
	
	@PostMapping("/addstrategy")
	public R addstrategy(ImageStrategy imagestrategy) {
		
		imagestrategy.setCreateTime(new Date());
		ImageStrategy r =imagestrategyrepository.save(imagestrategy);
		
		return R.ok(r);
		
	}
	
	
	

}

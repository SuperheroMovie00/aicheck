package com.aicheck.face.modules.advertisingImages.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.face.modules.advertisingImages.entity.Extend;
import com.aicheck.face.modules.advertisingImages.service.ExtendService;
import com.aicheck.face.vo.R;

@RestController
@RequestMapping("/v1/extend")
public class ExtendController {
	
	
	  @Autowired
	    private ExtendService extendservice;
	
	  
	  @GetMapping
	  public R findAll() {
		  List<Extend> extendlist =extendservice.findAll();
		  return R.ok(extendlist);
	  }
	  
	  @PostMapping("/addextend")
	  public R addextend(String name,String url) {
		  
		  Extend extend=new Extend();
		  extend.setName(name);
		  extend.setUrl(url);
		  Extend e=extendservice.save(extend);
		  
		  return R.ok(e);
		  
	  }
	  
	  
	  @PostMapping("/deleteextend")
	  public R deleteextend(Integer id) { 
		  try {
			  extendservice.delete(id);
			  return R.ok();
		} catch (Exception e) {
			  return R.error();
		}
		  
		
	  }
	  
	  
	  
	
	
	
	

}

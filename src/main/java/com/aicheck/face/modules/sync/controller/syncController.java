package com.aicheck.face.modules.sync.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.face.modules.sync.entity.sync;
import com.aicheck.face.modules.sync.service.syncService;
import com.aicheck.face.vo.R;


@RestController
@RequestMapping("/v1/sync")
public class syncController {
	@Autowired
	private  syncService syncservice;
	
	@PostMapping("/add")
	public R addsync(sync sync) {
		try {
			syncservice.save(sync);
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
	}

	
	
}

package com.aicheck.face.modules.visitorsRecord.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aicheck.face.modules.visitorsRecord.entity.Carstatistics;
import com.aicheck.face.modules.visitorsRecord.entity.VisitorsRecord;
import com.aicheck.face.modules.visitorsRecord.entity.carparameter;
import com.aicheck.face.modules.visitorsRecord.service.CarstatisticsService;
import com.aicheck.face.vo.R;

import aj.org.objectweb.asm.Type;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/v1/Carstatistics")
@Slf4j
public class CarstatisticsController {

	@Autowired
	private CarstatisticsService CarstatisticsService;
	
	
	
	
	@PostMapping("/delteall")
	public R delteall() {
		
	int r=CarstatisticsService.deleteall();
		if(r>0) {
			return R.ok();
		}else {
			return R.error();
		}

	}
	
	
	
	
	@PostMapping("/querycar")
	public R findAllList(int type,@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {
		
		if(type==1) {
			//date:2019-9-9     Page<Carstatistics> visitorsRecords = CarstatisticsService.findAll(currentPage, pageSize);
			List<Carstatistics> visitorsRecords=CarstatisticsService.querycarallforday(currentPage, pageSize);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("totalElements",CarstatisticsService.querycarallfordaycount());
			map.put("content", visitorsRecords);
			return R.ok(map);
		}else if(type==2) {
			List<Carstatistics> visitorsRecords = CarstatisticsService.querycarstatisforday(currentPage, pageSize);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("totalElements",CarstatisticsService.querycarstatisfordaycount());
			map.put("content", visitorsRecords);
			return R.ok(map);
		}else {
			return R.error("输入类型参数非法");
		}
		
	}
	
	
	@PostMapping("/addcarlicence")
	public R addcarlicence(@RequestBody String  carstring) {
		
		JSONObject jsonObj = JSONObject.fromObject(carstring);
		JSONObject jsonObjc=(JSONObject) jsonObj.get("carstring");
		carparameter carparameter = (carparameter) JSONObject.toBean(jsonObjc,carparameter.class);
		
		if(carparameter.getCarnumber()!=null && carparameter.getSchedule()!=null) {
			/**
			 * 车辆进入:
			 *    读取进度     ,进度为123正常显示    ,如果为4 则将状态赋值为无效   ,一个车重复来,
			 *    显示一条记录.   按照创建时间和状态去查询在列的车辆信息
			 *    如果 状态回退        =>
			 */
		Carstatistics carstatistics2=new Carstatistics();
		Carstatistics carstatistics=CarstatisticsService.selectcarstatisticsforcarnumber(carparameter.getCarnumber());
		if(carstatistics!=null) {
			if(carparameter.getSchedule()==4) {
				//为4状态的时候,将状态置成无效
				carstatistics.setStatus(0);
			}
			carstatistics.setCarnumber(carparameter.getCarnumber());
			carstatistics.setSchedule(carparameter.getSchedule());
			carstatistics.setModificationTime(new Date());
			carstatistics2=CarstatisticsService.save(carstatistics);
		}else {
			Carstatistics carstatistic=new Carstatistics();
			carstatistic.setCarnumber(carparameter.getCarnumber());
			carstatistic.setSchedule(carparameter.getSchedule());
			if(carparameter.getSchedule()==4) {
				carstatistic.setStatus(0);
			}
			carstatistic.setStatus(1);                     //最初添加的时候将状态赋值为1
			carstatistic.setCreateTime(new Date());
			carstatistics2=CarstatisticsService.save(carstatistic);
		}
		Map<String, String> cMap = new HashMap<String, String>();
		
		if(carstatistics2!=null) {
			if(carstatistics!=null) {
				cMap.put("msg", "修改成功");
				return R.ok(cMap);
			}else {
				cMap.put("msg", "新增成功");
				return R.ok(cMap);
			}
		}else {
			return R.error("新增失败");
		}
		}else {
			return R.error("车牌或状态为空");
		}
	
	}
	
}

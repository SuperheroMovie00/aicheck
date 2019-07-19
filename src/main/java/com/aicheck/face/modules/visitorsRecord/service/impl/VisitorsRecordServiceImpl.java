/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.visitorsRecord.service.impl;

import com.aicheck.face.modules.visitorsRecord.entity.VisitorsRecord;
import com.aicheck.face.modules.visitorsRecord.repository.VisitorsRecordRepository;
import com.aicheck.face.modules.visitorsRecord.service.VisitorsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 7:32 PM 2018/11/27
 */
@Service("visitorsRecordService")
public class VisitorsRecordServiceImpl implements VisitorsRecordService {

	@Autowired
	private VisitorsRecordRepository visitorsRecordRepository;

	@Override
	public synchronized VisitorsRecord save(VisitorsRecord visitorsRecord) {

		VisitorsRecord visit = visitorsRecordRepository.findLatestByCustomerId(visitorsRecord.getCustomerId());

		if (visit != null) {
			long timestamp = visit.getCreateTime().getTime();  //用创建时间进行比较
			long currentTimestamp = new Date().getTime(); 
			long diff = currentTimestamp - timestamp;   //创建时间与当前时间进行对比
			long minute = diff / 1000L / 60L;	
				
			if (minute < 720L) {                    	//两个小时
				//visit.setCreateTime(new Date());
				visit.setLastTime(new Date());         //赋值最后的修改时间
				return visitorsRecordRepository.save(visit);
			}else {         //如果超出规定的时间的话  ，新增一条访客记录
				
				return visitorsRecordRepository.save(visitorsRecord);
			}
		}
		return visitorsRecordRepository.save(visitorsRecord);
	}

	@Override
	public List<VisitorsRecord> save(List<VisitorsRecord> t) {
		return visitorsRecordRepository.saveAll(t);
	}

	@Override
	public void delete(Integer integer) {
		visitorsRecordRepository.deleteById(integer);
	}

	@Override
	public VisitorsRecord update(VisitorsRecord visitorsRecord) {
		return visitorsRecordRepository.save(visitorsRecord);
	}

	@Override
	public List<VisitorsRecord> find(VisitorsRecord visitorsRecord) {
		return null;
	}

	@Override
	public List<VisitorsRecord> findAll() {
		return visitorsRecordRepository.findAll();
	}

	@Override
	public VisitorsRecord findById(Integer integer) {
		Optional<VisitorsRecord> optionalVisitorsRecord = visitorsRecordRepository.findById(integer);
		return optionalVisitorsRecord.isPresent() ? optionalVisitorsRecord.get() : null;
	}

	
	@Override
	public VisitorsRecord findLatestOrderByCreateTime(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return visitorsRecordRepository.findLatestOrderByCreateTime(simpleDateFormat.format(date));
	}

	

	// 统计前的处理（去重处理，若干小时内进入多次算一次）
	@Override
	public void disposition() {
		long nd = 1000 * 24 * 60 * 60;// 每天毫秒数
		long nh = 1000 * 60 * 60;// 每小时毫秒数
		List<VisitorsRecord> vistorslist = visitorsRecordRepository.queryvisitorsfromprocess();
		String cusid;
		Date cretime;
		cusid = vistorslist.get(0).getCustomerId();
		cretime = vistorslist.get(0).getCreateTime();
		vistorslist.get(0).setProcessDup(1); // 将是否处理字段状态改为1
		vistorslist.get(0).setIsStat(1);
		visitorsRecordRepository.save(vistorslist.get(0));
		for (int r = 1; r < vistorslist.size(); r++) {
			// 将第一个设置为1 并且将初始的进行赋值
			String CustomerId=vistorslist.get(r).getCustomerId();
			if (CustomerId.equals("0")) {
				vistorslist.get(r).setProcessDup(1); // 将是否处理字段状态改为1
				vistorslist.get(r).setIsStat(1);
			} else {
				if (vistorslist.get(r).getCustomerId().equals(cusid)) {
					long diff = cretime.getTime()-vistorslist.get(r).getCreateTime().getTime();
					long hour = diff % nd / nh;
					if (hour < 5) { // 小于五小时（将赋值为）
						vistorslist.get(r).setProcessDup(1); // 将是否处理字段状态改为1
						vistorslist.get(r).setIsStat(0);
					} else {
						cusid = vistorslist.get(r).getCustomerId();
						cretime = vistorslist.get(r).getCreateTime(); // 将第一条数据
						vistorslist.get(r).setProcessDup(1); // 将是否处理字段状态改为1
						vistorslist.get(r).setIsStat(1);
					}
				} else {
					cusid = vistorslist.get(r).getCustomerId();
					cretime = vistorslist.get(r).getCreateTime();
					vistorslist.get(r).setProcessDup(1); // 将是否处理字段状态改为1
					vistorslist.get(r).setIsStat(1);
				}
			}
				visitorsRecordRepository.save(vistorslist.get(r));
		}
	}
}

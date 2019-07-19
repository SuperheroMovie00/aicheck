package com.aicheck.face.modules.tags.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.aicheck.face.modules.tags.entity.CustomerTags;
import com.aicheck.face.modules.tags.entity.Tags;
import com.aicheck.face.modules.tags.service.CustomerTagsService;
import com.aicheck.face.modules.tags.service.TagsService;
import com.aicheck.face.vo.R;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:53 PM 2018/11/26
 */
@RestController
@RequestMapping("/v1/customertags")
public class CustomerTagsController {
	@Autowired
	private CustomerTagsService CustomertagsService;
	@Autowired
    private TagsService tagsService;
		
	//新增一个关系记录
	 @PostMapping("/addcustomertags")
	 public R addcustomertags(CustomerTags customertags) {
		 customertags.setCreateTime(new Date());
		 CustomerTags custa=CustomertagsService.save(customertags);
		 return R.ok(custa);
	 }
	 
	 //新加的删除会员标签中间表记录
	 @PostMapping("/deletecustomertags")
	 public R deletecustomertags(@RequestParam(value = "customerId")Integer customerId,@RequestParam(value = "tagId")Integer tagId) {	 
		 int r=CustomertagsService.deletecustomertags(customerId, tagId); 
		 if(r>0) {
			 return R.ok(); 
		 }else {
			 return R.error();
		 }
	 }
	 
	 
	 @PostMapping("/addtagscustomer")
	 public R addtagscustomer(@RequestParam(value = "name")String name,@RequestParam(value = "customerId")Integer customerId) {
		
		 //新增一个标签
		 Tags tags=new Tags();
		 tags.setName(name);
		 tags.setCreateTime(new Date());
		 tags.setSort(0);
		 tags.setCreateId(1);
		 Tags r =tagsService.save(tags);
		
		 //将新增的标签与用户关联起来
		 CustomerTags ct=new CustomerTags();
		 ct.setTagId(r.getId());
		 ct.setCustomerId(customerId);
		 ct.setCreateTime(new Date());
		 CustomerTags custa=CustomertagsService.save(ct);
		 
		 if(custa!=null) {
			 return R.ok();
		 }else {
			 return R.error();
		 }
	 }
	 
	 
	 
	 
	

}

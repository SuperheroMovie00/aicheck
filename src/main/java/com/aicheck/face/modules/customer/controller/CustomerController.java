/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.customer.controller;

import com.aicheck.face.modules.pathseting.entity.pathseting;
import com.aicheck.face.modules.pathseting.service.pathsetingService;
import com.alibaba.fastjson.JSON;
import com.aicheck.face.common.constant.ResultEnum;
import com.aicheck.face.common.utils.*;
import com.aicheck.face.modules.customer.entity.Customer;
import com.aicheck.face.modules.customer.entity.CustomerForm;
import com.aicheck.face.modules.customer.entity.UserModel;
import com.aicheck.face.modules.customer.entity.requstss;
import com.aicheck.face.modules.customer.service.CustomerService;
import com.aicheck.face.modules.customer.utils.CustomerPushBoxUtils;
import com.aicheck.face.modules.customer.vo.CustomerVO;
import com.aicheck.face.modules.device.entity.Device;
import com.aicheck.face.modules.device.entity.TodoPush;
import com.aicheck.face.modules.device.service.DeviceService;
import com.aicheck.face.modules.device.service.TodoPushService;
import com.aicheck.face.modules.nettyPush.GlobalUser;
import com.aicheck.face.modules.nettyPush.Message;
import com.aicheck.face.modules.nettyPush.MessageTypeEnum;
import com.aicheck.face.modules.tags.entity.CustomerTags;
import com.aicheck.face.modules.tags.entity.Tags;
import com.aicheck.face.modules.tags.service.CustomerTagsService;
import com.aicheck.face.modules.tags.service.TagsService;
import com.aicheck.face.vo.R;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.aicheck.face.modules.customer.utils.ConverterUtils.converterCustomerVO;



/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:25 PM 2019/1/23
 */
@RestController
@RequestMapping("/v1/customer")
@Slf4j
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerTagsService customerTagsService;
	@Autowired
	private TagsService tagsService;

	@Autowired
	private pathsetingService pathsetingService;

	@Autowired
	private DeviceService deviceService;

	Thread thread = new Thread();

	@Autowired
	private TodoPushService todoPushservice;

	@GetMapping
	public R findAll(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
			@RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize) {

		Page<Customer> page = customerService.findAll(currentPage, pageSize);
		if(page==null){
			return R.error("@GetMapping=> page为空");
		}


		   @SuppressWarnings("unused")


	 	List<Customer> costomerlist = page.getContent();// 将分页中的数据取出进行排序
		List<CustomerVO> costomervolist =new ArrayList<>();
		for (int r=0;r<costomerlist.size();r++){
			CustomerVO c= converterCustomerVO(costomerlist.get(r));
			JSONObject jsonObject = JSONObject.fromObject(costomerlist.get(r).getUserModelValue());
			UserModel stu = (UserModel) JSONObject.toBean(jsonObject, UserModel.class);
			c.setUserModel(stu);
			costomervolist.add(c);
		}


         //userModelValue
		return R.ok(costomervolist).put("total", page.getTotalElements());
		//return R.ok(converterCustomerVOS(page.getContent())).put("total", page.getTotalElements());
	}

	/**
	 * @author
	 * @param id
	 * @return
	 */
	/*
	 * @PostMapping("/login") public R login(CustomerForm customerForm) { return
	 * R.ok(); }
	 */

	@GetMapping("/{id}")
	public R findById1(@PathVariable Integer id) {

		CustomerVO customerVO = findById(id);


		if (customerVO == null) {
			return R.error(ResultEnum.IS_NOT_EXIST.getCode(), ResultEnum.IS_NOT_EXIST.getMsg());
		}
		return R.ok(customerVO);
	}

	@PostMapping("/{id}")
	public R findById2(@PathVariable Integer id) {

		CustomerVO customerVO = findById(id);


		if (customerVO == null) {
			return R.error(ResultEnum.IS_NOT_EXIST.getCode(), ResultEnum.IS_NOT_EXIST.getMsg());
		}
		return R.ok(customerVO);
	}

	// @PostMapping("/check")
	// public R pushBoxCheck(MultipartFile file) {
	//
	// GlobalUser.channels.forEach(channel ->{
	// System.out.println(channel.id());
	// });
	//
	// return R.ok();
	// }

	private CustomerVO findById(Integer id) { // 根据id查询会员
		Customer customer = customerService.findById(id);
		if (customer == null) {
			return null;
		}
		CustomerVO customerVO = new CustomerVO();
		BeanUtils.copyProperties(customer, customerVO);
		customerVO.setCustomerId(String.valueOf(customer.getId()));

		List<CustomerTags> customerTags = customerTagsService.findByCustomerId(customer.getId());
		if(customerTags==null){
			return null;
		}

		if (!CollectionUtils.isEmpty(customerTags)) {
			List<Tags> tagsList = tagsService
					.findByIdIn(customerTags.stream().map(CustomerTags::getTagId).collect(Collectors.toList()));
			customerVO.setTags(tagsList);
		}

		if (!StringUtils.isEmpty(customer.getUserModelValue())) {

			customerVO.setUserModel(JSON.parseObject(customer.getUserModelValue(), UserModel.class));
		}
		return customerVO;
	}

	@GetMapping("/ids")
	public R findByIdIn(@RequestParam(value = "ids") String ids) {

		if (ids.length() > 100) {
			return R.ok();
		}
		String[] idsStr = ids.split(",");
		List<Integer> idList = com.aicheck.face.common.utils.BeanUtils.CollStringToIntegerLst(Arrays.asList(idsStr));
		List<Customer> customers = customerService.findByIdIn(idList.toArray(new Integer[ids.length()]));
		if(customers==null){
			return R.error("/ids=> customers为空");
		}

		CustomerVO customerVOer = findById(idList.get(0));

		// 废掉↓
		@SuppressWarnings("unused")
		List<CustomerVO> customerVOS = com.aicheck.face.common.utils.BeanUtils.batchTransform(CustomerVO.class,
				customers);
		
		return R.ok(customerVOer);
	}

	@PostMapping("/result")
	public R result(HttpServletRequest request, Integer todoid) {

		System.out.println(todoid);
		return null;
	}

	@PostMapping("/checkfaceresult")
	@ResponseBody
	public R checkfaceresult(@RequestBody @Valid requstss todoid) throws InterruptedException {
		Map<String, String> cMap = new HashMap<String, String>();
		TodoPush todoPush = todoPushservice.findById(todoid.getTodoid());



		if (todoPush == null) {
			return R.error();
		}

		int status = todoPush.getStatus();
		R r = new R();
		/**
		 * 如果todopush 的 status=0 说明还没有收到socket的返回值
		 */
		if (status == 0) {
			cMap.put("result", "2");
			r = R.ok(cMap);
		} else if (status == 1) {

			if (todoPush.getResult().equals("noface")) {
				cMap.put("result", "1");
				r = R.ok(cMap);
			} else {
				cMap.put("result", "0");
				cMap.put("customer_id", todoPush.getResult());
				r = R.ok(cMap);
			}
		} else {
			r = R.ok("未知原因，请求盒子出错！");
		}
		return r;
	}

	@PostMapping("/checkface")
	public R check(HttpServletRequest request, @RequestBody String features)  {
		TodoPushService todopushservice = (TodoPushService) SpringContextUtils.getBean("todoPushService");


		TodoPush todoPush = new TodoPush();
		todoPush.setType(3);
		todoPush.setStatus(0);
		todoPush.setMessage(features);
		todoPush.setCreateTime(new Date());
		todoPush.setDeviceId(request.getHeader("mac"));
		TodoPush todoPushdb = todopushservice.save(todoPush);


		if(todoPushdb!=null){
			log.info("todopusth新增成功=>");
		}else{
			log.info("todopusth新增失败=>");
			return R.error();
		}

		Integer id = todoPushdb.getId();

		List<Device> boxdevices = deviceService.findAllByforplatform("box");

		if(boxdevices==null){
			return R.error("/checkface=> boxdevices为空");
		}


		List<Channel> channellist = new ArrayList<>();

		GlobalUser.channels.forEach(channel -> {
			for (int r = 0; r < boxdevices.size(); r++) {
				if (channel.remoteAddress().toString().equals(boxdevices.get(r).getIpAddress())) {
					channellist.add(channel);
				}
			}
		});

		if (channellist.size() == 0) {
			return R.error("算力盒关闭，请开启");
		}

		int index = (int) (Math.random() * (channellist.size())); // 从1到10的int型随数
		Channel channel = channellist.get(index);

		Message message = new Message();
		message.setAction(MessageTypeEnum.CHECK_FACE.getValue());
		message.setId(id);
		message.setObject(features);
		String str = JSON.toJSONString(message);
		channel.writeAndFlush(new TextWebSocketFrame(str));

		Map<String, Integer> cMap = new HashMap<String, Integer>();
		cMap.put("todoid", id);
		return R.ok(cMap);

	}

	@PostMapping("/save")
	public R save2(CustomerForm customerForm) {

		log.info("进入注册***********************");

		Customer cus = customerService.findByMobile(customerForm.getMobile()); // 根据传输来会员表单的手机号查询会员

		if (!StringUtils.isEmpty(customerForm.getMobile()) && cus != null) { // 判断传输的手机号部位空并且查询的会员不为空
			return R.error(ResultEnum.MOBILE_REGISTER.getCode(), ResultEnum.MOBILE_REGISTER.getMsg());
		}

		Customer customer = new Customer();
		BeanUtils.copyProperties(customerForm, customer); // 将 customerForm对象映射到customer 对象中

		/**
		 * 传输参数中会员的userModel不为空 并且 userModel中的id值不为空
		 */
		if (customerForm.getUserModel() != null && customerForm.getUserModel().getUserId() != null) {
			// 将传输参数中的userModel赋值到modelvalue字段中
			customer.setUserModelValue(JSON.toJSONString(customerForm.getUserModel()));
		}

		// 取出数据库中会员表中的member_id 最大的
		String code = customerService.findByMaxCode();
		// 将以上取出的member_id值赋值到新的会员中member_id字段
		customer.setMemberId(PrimaryGenerate.getInstance().generaterNextNumber(code));
		Customer customerrequest = customerService.save(customer);

		// 添加标签关系
		Integer[] ids = customerForm.getTagIds();
		if (ids != null && ids.length > 0) {
			List<CustomerTags> customerTagsList = new ArrayList<>();

			for (int i = 0; i < ids.length; i++) {
				CustomerTags customerTags = new CustomerTags();
				customerTags.setCustomerId(1);
				customerTags.setTagId(ids[i]);
				customerTags.setCustomerId(customerrequest.getId());
				customerTagsList.add(customerTags);
			}
			customerTagsService.save(customerTagsList);
		}

		CustomerVO customerVO = new CustomerVO();

		BeanUtils.copyProperties(customerrequest, customerVO);
		// 推送 box 新增数据
		CustomerPushBoxUtils.nettyPush(MessageTypeEnum.SAVE.getValue(), customerVO);

		return R.ok(customerVO);
	}

	@PostMapping
	public R save(@RequestBody @Valid CustomerForm customerForm) {



		Customer cus = customerService.findByMobile(customerForm.getMobile()); // 根据传输来会员表单的手机号查询会员

		if (!StringUtils.isEmpty(customerForm.getMobile()) && cus != null) { // 判断传输的手机号部位空并且查询的会员不为空
			return R.error(ResultEnum.MOBILE_REGISTER.getCode(), ResultEnum.MOBILE_REGISTER.getMsg());
		}

		Customer customer = new Customer();
		BeanUtils.copyProperties(customerForm, customer); // 将 customerForm对象映射到customer 对象中

		/**
		 * 传输参数中会员的userModel不为空 并且 userModel中的id值不为空
		 */
		if (customerForm.getUserModel() != null && customerForm.getUserModel().getUserId() != null) {
			// 将传输参数中的userModel赋值到modelvalue字段中
			customer.setUserModelValue(JSON.toJSONString(customerForm.getUserModel()));
		}

		// 取出数据库中会员表中的member_id 最大的
		String code = customerService.findByMaxCode();
		// 将以上取出的member_id值赋值到新的会员中member_id字段
		customer.setMemberId(PrimaryGenerate.getInstance().generaterNextNumber(code));

		Customer customerrequest = customerService.save(customer);

		if(customerrequest==null){
			log.info("创建会员失败=》"+customer);
			return R.error("创建会员失败");
		}

		// 添加标签关系
		Integer[] ids = customerForm.getTagIds();
		if (ids != null && ids.length > 0) {
			List<CustomerTags> customerTagsList = new ArrayList<>();

			for (int i = 0; i < ids.length; i++) {
				CustomerTags customerTags = new CustomerTags();
				customerTags.setCustomerId(1);
				customerTags.setTagId(ids[i]);
				customerTags.setCustomerId(customerrequest.getId());
				customerTagsList.add(customerTags);
			}
			customerTagsService.save(customerTagsList);
		}
		CustomerVO customerVO = new CustomerVO();

		BeanUtils.copyProperties(customerrequest, customerVO);

		// 推送 box 新增数据
		// CustomerPushBoxUtils.nettyPush(MessageTypeEnum.SAVE.getValue(), customerVO);

		/**
		 * 直接写入tudu_push
		 */
		log.info("写入到同步程序开始**********");
		CustomerPushBoxUtils.nettyPusher(MessageTypeEnum.SAVE.getValue(), customerVO);
		log.info("写入到同步程序成功**********");

		/*
		 * List<Device> devices=deviceService.findAll();
		 * 
		 * for(int r=0;r<devices.size();r++) {
		 * 
		 * sync sy=new sync(); sy.setCreateTime(new Date()); sy.setFunc("save");
		 * sy.setStatus(0); sy.setDataId(customerrequest.getId());;
		 * sy.setReceiver(devices.get(r).getIpAddress()); syncservice.save(sy);
		 * //保存至sync表中
		 * 
		 * }
		 */
		return R.ok(customerVO);
	}

	@PostMapping("/update")
	public R update2(Integer id, CustomerForm customerForm) {

		Customer customer = customerService.findById(id);
		if (customer == null) {
			return R.error(ResultEnum.IS_NOT_EXIST.getCode(), ResultEnum.IS_NOT_EXIST.getMsg());
		}
		UpdateUtils.copyNullProperties(customerForm, customer);
		customer = customerService.update(customer);

		CustomerVO customerVO = new CustomerVO();

		BeanUtils.copyProperties(customer, customerVO);

		// 推送box 修改数据
		CustomerPushBoxUtils.nettyPush(MessageTypeEnum.UPDATE.getValue(), customerVO);
		// 修改标签
		/**
		 * 修改操作之前标签修改就已经完成（所以没有必要再麻烦修改）
		 */
		/*
		 * Integer[] ids = customerForm.getTagIds(); if (ids != null && ids.length > 0)
		 * { customerTagsService.deleteByCustomerId(id); List<CustomerTags>
		 * customerTagsList = new ArrayList<>(); for (int i = 0; i < ids.length; i++) {
		 * CustomerTags customerTags = new CustomerTags();
		 * customerTags.setCustomerId(1); customerTags.setTagId(ids[i]);
		 * customerTags.setCustomerId(customerVO.getId());
		 * customerTagsList.add(customerTags); }
		 * //customerTagsService.save(customerTagsList); } else {
		 * customerTagsService.deleteByCustomerId(id); }
		 */
		/**
		 * 修改操作之前标签修改就已经完成（所以没有必要再麻烦修改）结束
		 */

		return R.ok(customerVO);
	}

	@PutMapping("/{id}")
	public R update(@PathVariable Integer id, @RequestBody @Valid CustomerForm customerForm) {

		Customer customer = customerService.findById(id);
		if (customer == null) {
			return R.error(ResultEnum.IS_NOT_EXIST.getCode(), ResultEnum.IS_NOT_EXIST.getMsg());
		}
		UpdateUtils.copyNullProperties(customerForm, customer);
		customer = customerService.update(customer);

		CustomerVO customerVO = new CustomerVO();

		BeanUtils.copyProperties(customer, customerVO);

		// 推送box 修改数据
		CustomerPushBoxUtils.nettyPush(MessageTypeEnum.UPDATE.getValue(), customerVO);
		// 修改标签
		Integer[] ids = customerForm.getTagIds();
		if (ids != null && ids.length > 0) {
			customerTagsService.deleteByCustomerId(id);

			List<CustomerTags> customerTagsList = new ArrayList<>();

			for (int i = 0; i < ids.length; i++) {
				CustomerTags customerTags = new CustomerTags();
				customerTags.setCustomerId(1);
				customerTags.setTagId(ids[i]);
				customerTags.setCustomerId(customerVO.getId());
				customerTagsList.add(customerTags);
			}
			customerTagsService.save(customerTagsList);
		} else {
			customerTagsService.deleteByCustomerId(id);
		}
		return R.ok(customerVO);
	}

	// 新增的删除的功能
	@GetMapping("/dele")
	public R deleteforid(@RequestParam(value = "ids") int ids) {
		try {
			/*推送到设备*/
			List<Integer> idList=new ArrayList<>();
			idList.add(ids);
			customerService.delete(ids);
			CustomerPushBoxUtils.nettyPush(MessageTypeEnum.DELETE.getValue(), idList.toArray(new Integer[idList.size()]));
			log.info("用户删除成功");
			return R.ok("删除成功");
		} catch (Exception e) {
			return R.error(1, "失败");
		}
	}

	@DeleteMapping
	public R delete(@RequestParam(value = "ids") String ids) {

		String[] str = ids.split(",");
		List<Integer> idList = com.aicheck.face.common.utils.BeanUtils.CollStringToIntegerLst(Arrays.asList(str));
		customerService.delete(idList);
		// 推送box 删除数据
		CustomerPushBoxUtils.nettyPush(MessageTypeEnum.DELETE.getValue(), idList.toArray(new Integer[idList.size()]));
		return R.ok();
	}

	@GetMapping("/push/{type}")
	public R push(@PathVariable Integer type) {

		String messageType = "";

		if (type == 1) {
			messageType = "pull";
			CustomerPushBoxUtils.nettyPush(messageType, new int[] { 6 });
			return R.ok();
		} else if (type == 2) {
			messageType = "save";
		} else if (type == 3) {
			messageType = "update";
		} else if (type == 4) {
			messageType = "delete";
			CustomerPushBoxUtils.nettyPush(messageType, new int[] { 6 });
			return R.ok();
		}
		Customer customer = new Customer();
		customer.setId(1);
		customer.setName("测试人员");
		customer.setAddress("test");
		customer.setAge(11);
		customer.setEmail("11@qq.com");
		customer.setGender("男");
		customer.setMobile("13333333333");
		customer.setImgUrl("http://47.74.128.130:8090/images/e03370365488cceffd525a302c1a2fde.png");

		CustomerVO customerVO = new CustomerVO();

		BeanUtils.copyProperties(customer, customerVO);

		CustomerPushBoxUtils.nettyPush(messageType, customerVO);

		return R.ok(customerVO);
	}

	@RequestMapping("/export")
	public void exportExcel(HttpServletResponse response) {

		String[] s = { "名称", "年龄", "性别", "手机", "邮箱", "地址", "创建时间", "创建编号", "用户JSON", "头像", "脸部特征" };
		List<String> header = Arrays.asList(s);

		List<Customer> customerList = customerService.findAll();
		if (!CollectionUtils.isEmpty(customerList)) {
			List<List<String>> dataList = new ArrayList<>();
			customerList.forEach(customer -> {
				List<String> objectList = new ArrayList<>();
				// objectList.add(String.valueOf(customer.getId()));
				objectList.add(customer.getName());
				objectList.add(String.valueOf(customer.getAge()));
				objectList.add(customer.getGender());
				objectList.add(customer.getMobile());
				objectList.add(customer.getEmail());
				objectList.add(customer.getAddress());
				objectList.add(String.valueOf(customer.getCreateTime()));
				objectList.add(String.valueOf(customer.getCreateId()));
				objectList.add(customer.getUserModelValue());
				objectList.add(customer.getImgUrl());
				objectList.add(customer.getFaceId());
				dataList.add(objectList);
			});

			try {
				ExcelUtil.exportExcel(response, "customer", "user", header, dataList, 1);
			} catch (Exception e) {
				log.error("导出excel文档错误:" + e);
			}
		}

		try {
			ZipUtils.downLoadZip(response, PropertiesUtils.getInstance().getProperties("customer"));
		} catch (Exception e) {
			log.error("导出zip错误:{}", e.getMessage());
		}
	}

	@PostMapping("/import")
	public R importExcel(@RequestBody MultipartFile file) {

		// CommonsMultipartFile cf= (CommonsMultipartFile)file;
		// DiskFileItem fi = (DiskFileItem)cf.getFileItem();
		// File f = fi.getStoreLocation();
		File zip = new File(PropertiesUtils.getInstance().getProperties("customer") + "user.zip");
		try {
			file.transferTo(zip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ZipUtils.decompressed(PropertiesUtils.getInstance().getProperties("customer"),
				PropertiesUtils.getInstance().getProperties("customer") + "user.zip");

		try {
			List<Customer> customerList = ExcelUtil.importCustomerExcel("customer.xlsx",
					PropertiesUtils.getInstance().getProperties("customer") + "customer.xlsx");

			customerService.save(customerList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (zip.exists()) {
			zip.delete();
		}

		File excel = new File(PropertiesUtils.getInstance().getProperties("customer") + "customer.xlsx");

		if (excel.exists()) {
			excel.delete();
		}
		return R.ok();
	}

	@PostMapping("/upload")
	public R upload(@RequestBody MultipartFile file) {
		/* log.info("开始上传图片"); */
		// 服务器路径
		// String path = "/Users/liaojin/Desktop/opencv/";
		//String path = PropertiesUtils.getInstance().getProperties("customer");

		pathseting  pathempty=pathsetingService.findpathfortype("customer");
		String  path =pathempty.getPath();

		// String path = "/usr/Java/TRT/img/";
		String originalFilename = file.getOriginalFilename();

		String fileName = DigestUtils.md5DigestAsHex((originalFilename + new Date().getTime()).getBytes());

		try {
			FileUploadUtils.uploadFile(file.getBytes(), path, fileName + ".png");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// String localhost = "192.168.1.165";
		String localhost = "192.168.1.99";

		try {
			localhost = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		String url = "http://" +  localhost+ ":9090/aicheck-face/images/" + fileName + ".png";
		// String url = "http://" + localhost + ":8090/yy-face/yy-face/images/" +
		// fileName + ".png";
		// String url = "http://47.74.128.130:8090/images/" + fileName + ".png";
		// String url = "http://192.168.1.165:8090/images/" + fileName + ".png";

		log.info("图片上传成功——————————> {}", url);
		return R.ok(url);
	}

	@PostMapping("/upload/visitors")
	public R uploadVisitors(@RequestBody MultipartFile file) {
		log.info("开始上传图片");
		// 服务器路径
		// String path = "/Users/liaojin/Desktop/opencv/";
		//String path = PropertiesUtils.getInstance().getProperties("visitors");

		pathseting  pathempty=pathsetingService.findpathfortype("visitors");
		String  path =pathempty.getPath();


		// String path = "/usr/Java/TRT/img/";
		String originalFilename = file.getOriginalFilename();

		String fileName = DigestUtils.md5DigestAsHex((originalFilename + new Date().getTime()).getBytes());

		try {
			FileUploadUtils.uploadFile(file.getBytes(), path, fileName + ".png");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// String localhost = "192.168.1.165";
		String localhost = "192.168.1.99";

		try {
			localhost = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		String url = "http://" + localhost + ":9090/aicheck-face/visitors/" + fileName + ".png";
		// String url = "http://" + localhost + ":8090/yy-face/yy-face/images/" +
		// fileName + ".png";
		// String url = "http://47.74.128.130:8090/images/" + fileName + ".png";
		// String url = "http://192.168.1.165:8090/images/" + fileName + ".png";

		log.info("图片上传成功——————————> {}", url);
		return R.ok(url);
	}


	@PostMapping("/upload/ai")
	public R uploadai(@RequestBody MultipartFile file) {
		log.info("开始上传图片");
		// 服务器路径
		// String path = "/Users/liaojin/Desktop/opencv/";
		//String path = PropertiesUtils.getInstance().getProperties("visitors");

		pathseting  pathempty=pathsetingService.findpathfortype("ai");
		String  path =pathempty.getPath();


		// String path = "/usr/Java/TRT/img/";
		String originalFilename = file.getOriginalFilename();

		String fileName = DigestUtils.md5DigestAsHex((originalFilename + new Date().getTime()).getBytes());

		try {
			FileUploadUtils.uploadFile(file.getBytes(), path, fileName + ".png");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// String localhost = "192.168.1.165";
		String localhost = "192.168.1.99";

		try {
			localhost = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		String url = "http://" + localhost + ":9090/aicheck-face/ai/" + fileName + ".png";
		// String url = "http://" + localhost + ":8090/yy-face/yy-face/images/" +
		// fileName + ".png";
		// String url = "http://47.74.128.130:8090/images/" + fileName + ".png";
		// String url = "http://192.168.1.165:8090/images/" + fileName + ".png";

		log.info("图片上传成功——————————> {}", url);
		return R.ok(url);
	}








}

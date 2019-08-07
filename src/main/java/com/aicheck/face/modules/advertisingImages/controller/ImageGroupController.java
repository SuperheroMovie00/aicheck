/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.controller;

import com.aicheck.face.common.constant.ResultEnum;
import com.aicheck.face.common.utils.UpdateUtils;
import com.aicheck.face.modules.advertisingImages.entity.AdvertisingImages;
import com.aicheck.face.modules.advertisingImages.entity.ImageGroup;
import com.aicheck.face.modules.advertisingImages.form.ImageGroupForm;
import com.aicheck.face.modules.advertisingImages.service.AdvertisingImagesService;
import com.aicheck.face.modules.advertisingImages.service.ImageGroupService;
import com.aicheck.face.modules.advertisingImages.vo.ImageGroupVO;
import com.aicheck.face.modules.device.entity.Device;
import com.aicheck.face.modules.device.service.DeviceService;
import com.aicheck.face.vo.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:39 PM 2019/2/18
 */
@RestController
@RequestMapping("/v1/image-group")
public class ImageGroupController {
	@Autowired
	private ImageGroupService imageGroupService;
	@Autowired
	private AdvertisingImagesService advertisingImagesService;
	@Autowired
	private DeviceService deviceservice;

	@GetMapping
	public R findAll() {

			
		/**
		 * 查询默认分组是否存在（如果不存在，新建一个）
		 */
		ImageGroup defaultgroup=imageGroupService.querydefault();
		if(defaultgroup==null) {
			ImageGroup defaultimageGroup=new ImageGroup();
			defaultimageGroup.setName("default");
			defaultimageGroup.setDefaults("default");
			imageGroupService.save(defaultimageGroup);
		}

		List<ImageGroup> imageGroupList = imageGroupService.findallnotdefault();

		if(imageGroupList==null){
			return R.error("@GetMapping=> imageGroupList为空");
		}


		/*
		 * System.out.println("分组"+imageGroupList); List<ImageGroupVO> imageGroupVOList
		 * = com.aicheck.face.common.utils.BeanUtils.batchTransform(ImageGroupVO.class,
		 * imageGroupList);
		 * 
		 * imageGroupVOList.forEach(imageGroupVO -> { AdvertisingImages
		 * advertisingImages =
		 * advertisingImagesService.findByGroupIdSortAsc(imageGroupVO.getId());
		 * 
		 * if (advertisingImages != null &&
		 * StringUtils.isNotEmpty(advertisingImages.getUrl())) {
		 * imageGroupVO.setCoverImage(advertisingImages.getUrl()); } });
		 */
		return R.ok(imageGroupList);
		// return R.ok(imageGroupVOList);
	}

	@GetMapping("/parent")
	public R findByParentId(@RequestParam(value = "id", required = false) Integer id) {

		if (id == null) {
			List<ImageGroup> imageGroupList = imageGroupService.findParentGroup();
			if(imageGroupList==null){
				return R.error("/parent=> imageGroupList为空");
			}
			return R.ok(converter(imageGroupList));
		}

		List<ImageGroup> imageGroupList = imageGroupService.findByParent(id);
		if(imageGroupList==null){
			return R.error("/parent=> imageGroupList为空");
		}

		return R.ok(converter(imageGroupList));
	}

	private List<ImageGroupVO> converter(List<ImageGroup> imageGroupList) {
		List<ImageGroupVO> imageGroupVOList = com.aicheck.face.common.utils.BeanUtils.batchTransform(ImageGroupVO.class,
				imageGroupList);

		imageGroupVOList.forEach(imageGroupVO -> {
			AdvertisingImages advertisingImages = advertisingImagesService.findByGroupIdSortAsc(imageGroupVO.getId());


			if (advertisingImages != null && StringUtils.isNotEmpty(advertisingImages.getUrl())) {
				imageGroupVO.setCoverImage(advertisingImages.getUrl());
			}
		});
		return imageGroupVOList;
	}

	@GetMapping("/findById")
	public R newfindById(@RequestParam(value = "id", required = false) Integer id) {
		ImageGroup imageGroup = imageGroupService.findById(id);

		if (imageGroup == null) {
			return R.error(ResultEnum.IS_NOT_EXIST.getCode(), ResultEnum.IS_NOT_EXIST.getMsg());
		}
		return R.ok(imageGroup);
	}

	@GetMapping("/{id}")
	public R findById(@PathVariable Integer id) {
		ImageGroup imageGroup = imageGroupService.findById(id);


		if (imageGroup == null) {
			return R.error(ResultEnum.IS_NOT_EXIST.getCode(), ResultEnum.IS_NOT_EXIST.getMsg());
		}
		return R.ok(imageGroup);
	}

	// 新添加的修改方法
	@PostMapping("/updateimagegrouper")
	public R updateimagegrouper(Integer groupid, Integer index) {
		System.out.println("删除机器"+index+"分组id"+groupid);
		index=index-1;
		String DeviceIds = "";
		String indexchar = "";
		String Macaddress = "";
		String ip="";
		@SuppressWarnings("unused")
		String idsaddress="";
		String deviceids="";
		ImageGroup imageGroup = imageGroupService.findById(groupid);

		if(imageGroup==null){
			return R.error("//updateimagegrouper=> imageGroup为空");
		}
		//String[] chars = imageGroup.getDeviceIds().split("\\|"); // |管道符需要转译
		String[] chars = imageGroup.getDeviceShow().split("\\|"); // |管道符需要转译
		// chars[index]="";
		for (int r = 0; r < chars.length; r++) {
			if (r != index) {
				DeviceIds += chars[r] + "|";
			} else {
				indexchar = chars[r];
				String[] indexcharlist = indexchar.split(",");
				if(!indexchar.equals("") && indexcharlist.length==1) {
					ip = indexchar;
				}else {	
					ip = indexcharlist[1]; // 将要删除的设备的IP地址取出进行下一步操作
				}
				DeviceIds += "";
			}
		}
		
		if(DeviceIds.length()>0) {
		 deviceids = DeviceIds.substring(0, DeviceIds.length() - 1);	
		}
		imageGroup.setDeviceShow(deviceids);
		imageGroup.setMachinenum(imageGroup.getMachinenum() - 1);// 删除机器机器数减1
		Device device = deviceservice.findAllByforipaddress(ip);	
		
		String[] deviceidarr = device.getMacAddress().split(",");

		for (int c = 0; c < deviceidarr.length; c++) {
			if (deviceidarr[c] != null || deviceidarr[c] != "") {
				Macaddress += deviceidarr[c] + ",";
			}
		}
		String Macaddresss = Macaddress.substring(0, Macaddress.length() - 1);
		imageGroup.setDeviceIds(Macaddresss);
		ImageGroup imageg = imageGroupService.update(imageGroup);
		return R.ok(imageg);

	}

	
	// 新添加的修改方法
	@PostMapping("/updateimagegroup")
	public R updateimagegroup(Integer groupid, String device) {
		ImageGroup r = null;
		Device devicean=null;
		ImageGroup imageGroup = imageGroupService.findById(groupid);
		if(imageGroup==null){
			return R.error("//updateimagegroup=> imageGroup为空");
		}

		String deviceIds = imageGroup.getDeviceShow();
		String[] chars = device.split(" ");
		String charss="";
		if(!device.equals("") && chars.length==1) {
			charss=device;
			devicean = deviceservice.findAllByforipaddress(device);
		}else {
			charss=chars[0]+","+chars[1];
			 devicean = deviceservice.findAllByforipaddress(chars[1]);
		}
		
		String Macaddress = devicean.getMacAddress();
		if(imageGroup.getDeviceIds().equals("")) {
			imageGroup.setDeviceIds(Macaddress);
		}
		imageGroup.setDeviceIds(imageGroup.getDeviceIds() +","+ Macaddress);// 将加入新添加mac地址的新的device的地址重新赋值在此字段

		if (deviceIds == null || deviceIds.equals("")) {
			imageGroup.setDeviceShow(charss);
			imageGroup.setMachinenum(imageGroup.getMachinenum() + 1);
			r = imageGroupService.update(imageGroup);
		} else {
			imageGroup.setDeviceShow(deviceIds + "|" +charss);
			imageGroup.setMachinenum(imageGroup.getMachinenum() + 1); // 添加机器机器数加1
			r = imageGroupService.update(imageGroup);
		}
		return R.ok(r);
	}

	// 新添加的新增方法
	@PostMapping("/addimageGroup")
	public R addimageGroup(ImageGroupForm imageGroupForm) {
		ImageGroup imageGroup = new ImageGroup();
		if (imageGroupForm.getParentId() != null) {
			ImageGroup parent = imageGroupService.findById(imageGroupForm.getParentId());
			if(imageGroup==null){
				return R.error("//addimageGroup=> parent为空");
			}

			parent.getDeviceIds();
			imageGroup.setDeviceIds(parent.getDeviceIds());
		}

		BeanUtils.copyProperties(imageGroupForm, imageGroup);
		if (imageGroupForm.getDevices() != null && imageGroupForm.getDevices().length > 0) {
			imageGroup.setDeviceIds(StringUtils.join(imageGroupForm.getDevices(), ","));
		}
		if (imageGroupForm.getGroupType() == null) {
			imageGroup.setGroupType(1);
		}
		if (imageGroupForm.getDataType() == null) {
			imageGroup.setDataType(1);
		}
		imageGroup = imageGroupService.save(imageGroup);
		return R.ok(imageGroup);
	}

	@PostMapping
	public R save(@RequestBody ImageGroupForm imageGroupForm) {

		// System.out.println("红火火恍恍惚惚"+imageGroupForm);
		ImageGroup imageGroup = new ImageGroup();
		if (imageGroupForm.getParentId() != null) {
			ImageGroup parent = imageGroupService.findById(imageGroupForm.getParentId());
			if(imageGroup==null){
				return R.error("//@PostMapping=> parent为空");
			}
			parent.getDeviceIds();
			imageGroup.setDeviceIds(parent.getDeviceIds());
		}

		BeanUtils.copyProperties(imageGroupForm, imageGroup);
		if (imageGroupForm.getDevices() != null && imageGroupForm.getDevices().length > 0) {
			imageGroup.setDeviceIds(StringUtils.join(imageGroupForm.getDevices(), ","));
		}
		if (imageGroupForm.getGroupType() == null) {
			imageGroup.setGroupType(1);
		}
		if (imageGroupForm.getDataType() == null) {
			imageGroup.setDataType(1);
		}
		imageGroup = imageGroupService.save(imageGroup);
		return R.ok(imageGroup);
	}

	@PutMapping("/{id}")
	public R update(@PathVariable Integer id, @RequestBody ImageGroupForm imageGroupForm) {

		List<ImageGroup> imageGroupList = imageGroupService.findByParent(id);

		if (!CollectionUtils.isEmpty(imageGroupList)) {

			for (ImageGroup imageGroup : imageGroupList) {
				String ids = StringUtils.join(imageGroupForm.getDevices(), ",");
				if (StringUtils.isNotEmpty(ids)) {
					imageGroupService.updateDeviceIds(ids, imageGroup.getId());
				}
			}
		}

		ImageGroup imageGroup = imageGroupService.findById(id);

		if (imageGroup == null) {
			return R.error(ResultEnum.IS_NOT_EXIST.getCode(), ResultEnum.IS_NOT_EXIST.getMsg());
		}
		UpdateUtils.copyNullProperties(imageGroupForm, imageGroup);

		imageGroup.setDeviceIds(StringUtils.join(imageGroupForm.getDevices(), ","));

		imageGroup = imageGroupService.update(imageGroup);

		return R.ok(imageGroup);
	}

	// 新加的删除分组的方法
	@PostMapping("/deletegroup")
	public R deletegroup(Integer id) {
		List<ImageGroup> imageGroupList = imageGroupService.findByParent(id);

		if (!CollectionUtils.isEmpty(imageGroupList)) {
			List<Integer> idList = imageGroupList.stream().map(ImageGroup::getId).collect(Collectors.toList());
			imageGroupService.deleteByIdIn(idList);
		}
		imageGroupService.delete(id);
		return R.ok();
	}

	@DeleteMapping("/{id}")
	public R delete(@PathVariable Integer id) {

		List<ImageGroup> imageGroupList = imageGroupService.findByParent(id);

		if (!CollectionUtils.isEmpty(imageGroupList)) {
			List<Integer> idList = imageGroupList.stream().map(ImageGroup::getId).collect(Collectors.toList());
			imageGroupService.deleteByIdIn(idList);
		}

		imageGroupService.delete(id);

		return R.ok();
	}

}

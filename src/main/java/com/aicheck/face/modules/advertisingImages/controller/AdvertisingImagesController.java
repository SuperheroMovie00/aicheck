/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.controller;

import com.alibaba.fastjson.JSON;
import com.aicheck.face.common.constant.ResultEnum;
import com.aicheck.face.common.utils.BeanUtils;
import com.aicheck.face.common.utils.FileUploadUtils;
import com.aicheck.face.common.utils.FileUtils;
import com.aicheck.face.common.utils.PropertiesUtils;
import com.aicheck.face.modules.advertisingImages.entity.AdvertisingImages;
import com.aicheck.face.modules.advertisingImages.entity.ImageGroup;
import com.aicheck.face.modules.advertisingImages.service.AdvertisingImagesService;
import com.aicheck.face.modules.advertisingImages.service.ImageGroupService;
import com.aicheck.face.modules.advertisingImages.utils.FileSuffixUtils;
import com.aicheck.face.modules.advertisingImages.vo.AdvertisingImagesInfoVO;
import com.aicheck.face.modules.advertisingImages.vo.AdvertisingImagesVO;
import com.aicheck.face.modules.device.entity.Device;
import com.aicheck.face.modules.device.service.DeviceService;
import com.aicheck.face.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:14 PM 2019/1/21
 */
@Slf4j
@RestController
@RequestMapping("/v1/advertising-images")
public class AdvertisingImagesController {

    @Autowired
    private AdvertisingImagesService advertisingImagesService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ImageGroupService imageGroupService;

    @GetMapping
    public R findAll() {

        List<AdvertisingImages> advertisingImagesList = advertisingImagesService.findAll();
        if(advertisingImagesList==null){
            return R.error("Get=>  advertisingImagesList为空");
        }
        return R.ok(advertisingImagesList);
    }

    @SuppressWarnings("static-access")
	@GetMapping("/ids")
    public R findByIds(@RequestParam(value = "ids") String ids) throws IOException {

        String[] array = ids.split(",");

        List<Integer> idList=  BeanUtils.CollStringToIntegerLst(Arrays.asList(array));

        List<AdvertisingImages> advertisingImagesList = advertisingImagesService.findByIds(idList);

        if(advertisingImagesList==null){
            return R.error("ids=>  advertisingImagesList为空");
        }

        List<AdvertisingImagesVO> advertisingImagesVOList = BeanUtils.batchTransform(AdvertisingImagesVO.class,advertisingImagesList);
        
        for(int r =0;r<advertisingImagesList.size();r++) {
        	
        	FileUtils file=new FileUtils();
        		
        	advertisingImagesVOList.get(r).setImage(file.getImageBase64Str(advertisingImagesList.get(r).getImage()));
        }
        
        return R.ok(advertisingImagesVOList);
    }

    @GetMapping("/device/{deviceId}")
    public R findByDevice(@PathVariable String deviceId) {

        Device device = deviceService.findByMacAddresser(deviceId);


        if (device == null || "BOX".equals(device.getPlatform().toUpperCase())) {
            return findDefault();
        }

        List<Device> deviceList = deviceService.findByNotGroupDevice();

        if(deviceList==null){
            return R.error("/device/{deviceId}=>  deviceList为空");
        }


        if (!CollectionUtils.isEmpty(deviceList)) {
            List<Integer> list = deviceList.stream().map(Device::getId).collect(Collectors.toList());

            if (list.contains(device.getId())) {
                return findDefault();
            }
        }

        List<AdvertisingImages> advertisingImagesList = advertisingImagesService.findByDeviceId(device.getId());

        if(advertisingImagesList==null){
            return R.error("/device/{deviceId}=>  advertisingImagesList为空");
        }
        
        
        return R.ok(advertisingImagesList);
    }
    
    //根据mac地址查询资源测试
    @PostMapping("/test")
    public R test( String deviceId) {
    	 List<AdvertisingImages> advertisingImagesList = advertisingImagesService.findByDeviceIdAi(deviceId);
        if(advertisingImagesList==null){
            return R.error("/Detest=>  advertisingImagesList为空");
        }


        log.info("资源=》"+advertisingImagesList);
    	 return R.ok(advertisingImagesList);
    }


    /**
     * 原来版本 default
     * @return
     */
    @GetMapping("/device-ai/{deviceId}")
    public R findByDeviceAi(@PathVariable String deviceId) {

        List<AdvertisingImages> advertisingImagesList = advertisingImagesService.findByDeviceIdAi(deviceId);



        if (CollectionUtils.isEmpty(advertisingImagesList)) {

            return R.ok(findDefaultAi());
        }

        List<Integer> groupIds = advertisingImagesList.stream().map(AdvertisingImages::getGroupId).collect(Collectors.toList());
        Set<Integer> set = new HashSet<>(groupIds);

        List<AdvertisingImagesInfoVO> advertisingImagesInfoVOList = new ArrayList<>();
        for (Integer integer : set) {

            ImageGroup imageGroup = imageGroupService.findById(integer);

            if(imageGroup==null){
                return R.error("/device-ai/{deviceId}=>  imageGroup为空");
            }

            AdvertisingImagesInfoVO advertisingImagesInfoVO = new AdvertisingImagesInfoVO();
            advertisingImagesInfoVO.setGroupId(integer);
            advertisingImagesInfoVO.setStrategy(imageGroup.getStrategy());
            advertisingImagesInfoVO.setStrategyType(imageGroup.getStrategyType());

            List<Integer> idList = new ArrayList<>();
            for (AdvertisingImages advertisingImages : advertisingImagesList) {
                if (integer == advertisingImages.getGroupId()) {
                    idList.add(advertisingImages.getId());
                }

            }
            advertisingImagesInfoVO.setIdList(idList);

            advertisingImagesInfoVOList.add(advertisingImagesInfoVO);

        }
        /**
         * 获取初始默认的分组中的资源
         */
        List<AdvertisingImages> defaultList = advertisingImagesService.findByGroupId(0);

        if (!CollectionUtils.isEmpty(defaultList)) {
            AdvertisingImagesInfoVO advertisingImagesInfoVO = new AdvertisingImagesInfoVO();
            advertisingImagesInfoVO.setGroupId(0);
            advertisingImagesInfoVO.setStrategy("default");
            advertisingImagesInfoVO.setIdList(defaultList.stream().map(AdvertisingImages::getId).collect(Collectors.toList()));
            advertisingImagesInfoVOList.add(advertisingImagesInfoVO);
        }

        return R.ok(advertisingImagesInfoVOList);

    }
    
    //新加的获取策略资源的方法
    @PostMapping("/advertisingImagesRepository")
    public R advertisingImagesRepository(Integer strategyid) {
    	List<AdvertisingImages> list=advertisingImagesService.qyeryadvertisingimagesforstrategyid(strategyid);
        if(list==null){
            return R.error("advertisingImagesRepository=>  list为空");
        }
    	return R.ok(list);
    	
    }
    
    //新加的获取策略资源的方法
    @PostMapping("/advertisingImagesforgroupid")
    public R advertisingImagesforgroupid(Integer groupid) {
    	List<AdvertisingImages> list=advertisingImagesService.qyeryadvertisingimagesforgroupid(groupid);
        if(list==null){
            return R.error("advertisingImagesforgroupid=>  list为空");
        }
    	return R.ok(list);
    }
    
    
  //新加的获取策略资源的方法
    @PostMapping("/deleteadviceimage")
    public R deleteadviceimage(Integer id) {
    	try {
    		advertisingImagesService.delete(id);

    		return R.ok();
		} catch (Exception e) {
			// TODO: handle exception
			return R.error();
		}
    }


    /**
     * 原来版本 default
     * @return
     */
    private List<AdvertisingImagesInfoVO> findDefaultAi() {

        List<AdvertisingImages> advertisingImagesList = advertisingImagesService.findByGroupId(1);

        if (CollectionUtils.isEmpty(advertisingImagesList)) {
            return null;
        }
        AdvertisingImagesInfoVO advertisingImagesInfoVO = new AdvertisingImagesInfoVO();
        advertisingImagesInfoVO.setGroupId(1);
        advertisingImagesInfoVO.setStrategy("default");
        advertisingImagesInfoVO.setIdList(advertisingImagesList.stream().map(AdvertisingImages::getId).collect(Collectors.toList()));
        List<AdvertisingImagesInfoVO> advertisingImagesInfoVOList = new ArrayList<>();
        advertisingImagesInfoVOList.add(advertisingImagesInfoVO);
        return advertisingImagesInfoVOList;
    }
    
    @GetMapping("/default")
    public R findDefault() {

        ImageGroup imageGroup=imageGroupService.querydefault();
        List<AdvertisingImages> advertisingImagesList = advertisingImagesService.findByGroupId(imageGroup.getId());

        if (advertisingImagesList==null){
            return R.error("/default=> advertisingImagesList为空");
        }

        return R.ok(advertisingImagesList);
    }
    
    
    //新加的新增方法
    @PostMapping("/addadvertisingimages")
    public R addadvertisingimages(AdvertisingImages advertisingimages) {
    	advertisingimages.setCreateTime(new Date());
    	AdvertisingImages adv=advertisingImagesService.save(advertisingimages);

    	if (adv==null){
            return R.error("/addadvertisingimages=> adv为空");
        }

    	return R.ok(adv);
    }
    
    //新加的新增方法
    @PostMapping("/defaultaddadvertisingimages")
    public R defaultaddadvertisingimages(AdvertisingImages advertisingimages) {

        ImageGroup imageGroup=imageGroupService.querydefault();

        advertisingimages.setGroupId(imageGroup.getId());
    	advertisingimages.setCreateTime(new Date());
    	AdvertisingImages adv=advertisingImagesService.save(advertisingimages);
        if (adv==null){
            return R.error("/defaultaddadvertisingimages=> adv为空");
        }

    	return R.ok(adv);
    }
    
   
    @GetMapping("/group/{groupId}")
    public R findByGroup(@PathVariable Integer groupId) {

        List<AdvertisingImages> advertisingImagesList = advertisingImagesService.findByGroupId(groupId);


        if (!CollectionUtils.isEmpty(advertisingImagesList)) {
            List<AdvertisingImagesVO> advertisingImagesVOList = BeanUtils.batchTransform(AdvertisingImagesVO.class,advertisingImagesList);
            return R.ok(advertisingImagesVOList);
        }
        return R.ok(advertisingImagesList);
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable Integer id) {

        AdvertisingImages advertisingImages = advertisingImagesService.findById(id);



        if (advertisingImages == null) {
            return R.error(ResultEnum.IS_NOT_EXIST.getCode(), ResultEnum.IS_NOT_EXIST.getMsg());
        }

        return R.ok(advertisingImages);
    }

//    @PostMapping
//    public R save(@RequestBody AdvertisingImagesForm advertisingImagesForm) {
//        List<AdvertisingImages> advertisingImagesList = new ArrayList<>();
//        String[] images = advertisingImagesForm.getImages();
//        for (int i = 0; i < advertisingImagesForm.getImages().length; i++) {
//            AdvertisingImages advertisingImages = new AdvertisingImages();
//            try {
//                byte[] bytes = base64Decoder.decodeBuffer(images[i]);
//                advertisingImages.setImage(bytes);
//                advertisingImages.setGroupId(advertisingImagesForm.getGroupId());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            advertisingImagesList.add(advertisingImages);
//        }
//
//        advertisingImagesList = advertisingImagesService.save(advertisingImagesList);
//
//        return R.ok(advertisingImagesList);
//    }
     
    	
    
    @PostMapping
    public R save(@RequestBody List<MultipartFile> files,@RequestParam(value = "groupId") Integer groupId) {
        List<AdvertisingImages> advertisingImagesList = new ArrayList<>();
        log.info("文件数量:{}",CollectionUtils.isEmpty(files)?0:files.size());
        files.forEach(file -> {

            try {
                FileUploadUtils.uploadFile(file.getBytes(),PropertiesUtils.getInstance().getProperties("ai"),file.getOriginalFilename());
            } catch (Exception e) {
               log.error("上传文件失败:{}",e.getMessage());
            }

            String localhost = "192.168.1.99";

            try {
                localhost = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error("获取本地地址失败:{}",e.getMessage());
            }

            String url = "http://" + localhost + ":9090/aicheck-face/ai/" + file.getOriginalFilename();

            AdvertisingImages advertisingImages = new AdvertisingImages();
            advertisingImages.setType(FileSuffixUtils.checkSuffix(file.getOriginalFilename())?1:2);
            advertisingImages.setGroupId(groupId);
            advertisingImages.setUrl(url);
            advertisingImagesList.add(advertisingImages);
        });

        log.info("保存文件数据:{}",JSON.toJSONString(advertisingImagesList));

        advertisingImagesService.save(advertisingImagesList);

        return R.ok();
    }

    @PutMapping
    public R update(@RequestBody Integer[] ids) {

        if (ids == null || ids.length == 0) {
            return R.error(ResultEnum.IS_NOT_EXIST.getCode(), ResultEnum.IS_NOT_EXIST.getMsg());
        }

        for (int i = 0; i < ids.length; i++) {
            AdvertisingImages advertisingImages = advertisingImagesService.findById(ids[i]);



            if (advertisingImages == null) {
                continue;
            }
            advertisingImages.setSort(i + 1);
            advertisingImagesService.update(advertisingImages);
        }

        return R.ok();
    }

    @DeleteMapping
    public R delete(@RequestParam String ids) {
        String[] str = ids.split(",");
        List<Integer> idList = com.aicheck.face.common.utils.BeanUtils.CollStringToIntegerLst(Arrays.asList(str));
        advertisingImagesService.delete(idList);
        return R.ok();
    }
    
    /**
     * 查询默认分组的资源
     * @return
     */
    @GetMapping("/qyeryadvertisingimagesforgroupidwherezero")
    public R qyeryadvertisingimagesforgroupidwherezero() {

        ImageGroup imageGroup=imageGroupService.querydefault();
        List<AdvertisingImages> advertisingimagess= advertisingImagesService.findByGroupId(imageGroup.getId());

        if (advertisingimagess==null){
            return R.error(" /qyeryadvertisingimagesforgroupidwherezero=> advertisingimagess为空");
        }

        return R.ok(advertisingimagess);
    }

    
}

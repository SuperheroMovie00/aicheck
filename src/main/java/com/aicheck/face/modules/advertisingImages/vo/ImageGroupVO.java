/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.vo;

import com.aicheck.face.common.utils.BeanUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:18 PM 2019/2/18
 */
public class ImageGroupVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String deviceIds;
    			
    private Integer[] devices;

    private String name;

    private Integer groupType;

    private Date createTime;

    private String coverImage;

    private String strategy;

    private Integer strategyType;

    private Integer dataType;

    public Integer getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(Integer strategyType) {
        this.strategyType = strategyType;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer[] getDevices() {
        return devices;
    }

    public void setDevices(Integer[] devices) {
        this.devices = devices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {

        this.deviceIds = deviceIds;

        List<Integer> integerList = BeanUtils.CollStringToIntegerLst(Arrays.asList(deviceIds.split(",")));

        this.devices = integerList.toArray(new Integer[integerList.size()]);

    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}

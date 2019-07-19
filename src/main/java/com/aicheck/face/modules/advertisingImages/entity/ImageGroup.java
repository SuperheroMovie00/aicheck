/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:16 PM 2019/2/18
 */
@Entity
@Table(name = "image_group")
public class ImageGroup implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String deviceIds;

    private Integer groupType;

    private String name;
    @CreationTimestamp
    private Date createTime;

    private Integer parentId;

    private Integer strategyType;

    private String strategy;

    private Integer dataType;
    
    private Integer machinenum;
    
    private String deviceShow;
    
    public String getDeviceShow() {
		return deviceShow;
	}

	public void setDeviceShow(String deviceShow) {
		this.deviceShow = deviceShow;
	}

	public Integer getMachinenum() {
		return machinenum;
	}

	public void setMachinenum(Integer machinenum) {
		this.machinenum = machinenum;
	}

	public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public Integer getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(Integer strategyType) {
        this.strategyType = strategyType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

	@Override
	public String toString() {
		return "ImageGroup [id=" + id + ", deviceIds=" + deviceIds + ", groupType=" + groupType + ", name=" + name
				+ ", createTime=" + createTime + ", parentId=" + parentId + ", strategyType=" + strategyType
				+ ", strategy=" + strategy + ", dataType=" + dataType + "]";
	}
    
    
}

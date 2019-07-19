/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:05 PM 2019/2/25
 */
public class AdvertisingImagesInfoVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer groupId;
    private Integer strategyType;
    private String strategy;

    private List<Integer> idList;


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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }
}

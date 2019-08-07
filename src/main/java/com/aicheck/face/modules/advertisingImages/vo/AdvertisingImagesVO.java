/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.vo;


import java.util.Date;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 10:38 AM 2019/2/19
 */
public class AdvertisingImagesVO {

    private Integer id;

    private String url;

    private Integer type;
    /**播放顺序*/
    private Integer sort;
    /**播放分组*/
    private Integer groupId;
    /**播放时间*/
    private Date createTime;
   
    private String image;
    	
    

    public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

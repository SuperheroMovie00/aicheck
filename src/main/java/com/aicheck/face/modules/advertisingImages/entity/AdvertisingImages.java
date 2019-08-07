/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 1:28 PM 2019/1/21
 */

@Entity
@Table(name = "advertising_images")
public class AdvertisingImages implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**主键*/
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    /**图片*/
    private String url;
    /**文件类型*/
    private Integer type;
    /**播放顺序*/
    private Integer sort;
    /**播放分组*/
    private Integer groupId;
    /**播放时间*/
    private Date createTime;
    
    private int strategy_id;
    
    private String image;
    
    

    public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public Integer getId() {
        return id;
    }

    
    public int getStrategy_id() {
		return strategy_id;
	}


	public void setStrategy_id(int strategy_id) {
		this.strategy_id = strategy_id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

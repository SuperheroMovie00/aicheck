/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.visitorsRecord.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 7:26 PM 2018/11/27
 */
@Entity
@Table(name = "visitors_record")
public class VisitorsRecord implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private Integer age;
    private String gender;
    private String imgUrl;
    private String customerId;
   
    private String faceId;
    @CreationTimestamp
    private Date createTime;
    private Date lastTime;
    
    /** 人脸坐标 */
    private String faceCoordinates;
    
    
    private Integer isStat;    		//是否 统计
    private Integer processDup;    //是否重复处理

    
    
    
    public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getIsStat() {
		return isStat;
	}

	public void setIsStat(Integer isStat) {
		this.isStat = isStat;
	}

	public Integer getProcessDup() {
		return processDup;
	}

	public void setProcessDup(Integer processDup) {
		this.processDup = processDup;
	}

	public String getFaceCoordinates() {
        return faceCoordinates;
    }

    public void setFaceCoordinates(String faceCoordinates) {
        this.faceCoordinates = faceCoordinates;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	@Override
	public String toString() {
		return "VisitorsRecord [id=" + id + ", age=" + age + ", gender=" + gender + ", imgUrl=" + imgUrl
				+ ", customerId=" + customerId + ", faceId=" + faceId + ", createTime=" + createTime
				+ ", faceCoordinates=" + faceCoordinates + ", isStat=" + isStat + ", processDup=" + processDup + "]";
	}
    
    
}

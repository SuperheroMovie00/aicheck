/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.identifyRecord.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 10:35 AM 2018/11/23
 */
@Entity
@Table(name = "identify_record")
public class IdentifyRecord implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 主键
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    /**
     * faceid
     */
    private String customerId;
    /**
     * 创建时间
     */
    @CreationTimestamp
    private Date createTime;
    /**
     * 人脸坐标位置
     */
    private String faceCoordinates;

    public String getFaceCoordinates() {
        return faceCoordinates;
    }

    public void setFaceCoordinates(String faceCoordinates) {
        this.faceCoordinates = faceCoordinates;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}

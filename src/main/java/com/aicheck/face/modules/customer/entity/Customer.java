/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.customer.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: liaojin
 * @Description: 客户
 * @Date: Created in 5:20 PM 2018/11/21
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {
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
     * 会员id
     */
    private String memberId;
    /**
     * 会员级别
     */
    private Integer level;
    /**
     * 客户名称
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private String gender;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 地址
     */
    private String address;
    /**
     * 创建时间
     */
    @CreationTimestamp
    private Date createTime;
    /**
     * 创建人
     */
    private Integer createId;
    /**
     * 头像url
     */
    private String imgUrl;

    private String userModelValue;

    private String faceId;
    
    private String interestName;

	public String getInterestName() {
		return interestName;
	}

	public void setInterestName(String interestName) {
		this.interestName = interestName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUserModelValue() {
        return userModelValue;
    }

    public void setUserModelValue(String userModelValue) {
        this.userModelValue = userModelValue;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

	@Override
	public String toString() {
		return "Customer [id=" + id + ", memberId=" + memberId + ", level=" + level + ", name=" + name + ", age=" + age
				+ ", gender=" + gender + ", mobile=" + mobile + ", email=" + email + ", address=" + address
				+ ", createTime=" + createTime + ", createId=" + createId + ", imgUrl=" + imgUrl + ", userModelValue="
				+ userModelValue + ", faceId=" + faceId + ", interestName=" + interestName + "]";
	}
    
    
    
}

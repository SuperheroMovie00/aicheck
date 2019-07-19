/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.customer.entity;


import java.util.Arrays;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:07 PM 2018/11/22
 */
public class CustomerForm {

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
    @NotEmpty
    private String name;
    /**
     * 年龄
     */
    @NotNull
    private Integer age;
    /**
     * 性别
     */
    @NotEmpty
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

    private UserModel userModel;
    /**
     * 头像url
     */
    @NotEmpty
    private String imgUrl;
    /**
     * 标签数组
     */
    private Integer[] tagIds;

    private String faceId;
    
    private String interestName;
    
 

	public String getInterestName() {
		return interestName;
	}

	public void setInterestName(String interestName) {
		this.interestName = interestName;
	}

	public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer[] getTagIds() {
        return tagIds;
    }

    public void setTagIds(Integer[] tagIds) {
        this.tagIds = tagIds;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

	@Override
	public String toString() {
		return "CustomerForm [memberId=" + memberId + ", level=" + level + ", name=" + name + ", age=" + age
				+ ", gender=" + gender + ", mobile=" + mobile + ", email=" + email + ", address=" + address
				+ ", userModel=" + userModel + ", imgUrl=" + imgUrl + ", tagIds=" + Arrays.toString(tagIds)
				+ ", faceId=" + faceId + ", interestName=" + interestName + "]";
	}
    
    
}

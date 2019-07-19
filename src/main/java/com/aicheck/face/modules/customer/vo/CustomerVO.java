/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.customer.vo;



import com.aicheck.face.modules.customer.entity.UserModel;
import com.aicheck.face.modules.tags.entity.Tags;

import java.util.Date;
import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:20 PM 2018/11/22
 */
public class CustomerVO {

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
    private Date createTime;
    /**
     * 创建人
     */
    private Integer createId;
    /**
     * 头像id
     */
    private String imgUrl;

    /**
     * 标签
     */
    private List<Tags> tags;

    private UserModel userModel;

    private String customerId;

    private String customerName;
    
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
        this.customerId = String.valueOf(id);

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


    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

	@Override
	public String toString() {
		return "CustomerVO [id=" + id + ", memberId=" + memberId + ", level=" + level + ", name=" + name + ", age="
				+ age + ", gender=" + gender + ", mobile=" + mobile + ", email=" + email + ", address=" + address
				+ ", createTime=" + createTime + ", createId=" + createId + ", imgUrl=" + imgUrl + ", tags=" + tags
				+ ", userModel=" + userModel + ", customerId=" + customerId + ", customerName=" + customerName
				+ ", interestName=" + interestName + "]";
	}

    
}

/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.visitorsRecord.entity;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 4:36 PM 2018/12/12
 */
public class VisitorsRecordForm {
    private Integer age;
    private String gender;
    private String imgUrl;
    private String customerId;
    private String faceId;
    /** 人脸坐标位置 */
    private String faceCoordinates;

    public String getFaceCoordinates() {
        return faceCoordinates;
    }

    public void setFaceCoordinates(String faceCoordinates) {
        this.faceCoordinates = faceCoordinates;
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

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}

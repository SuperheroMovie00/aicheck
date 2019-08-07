/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.customer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:57 PM 2018/11/26
 */
public class UserModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public long id;
    public String name;
    public String userId;
    public String localImagePath;
    public String urlImagePath;
    public float compareScore;
    public List<Float> features;
    public boolean serverSync;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocalImagePath() {
        return localImagePath;
    }

    public void setLocalImagePath(String localImagePath) {
        this.localImagePath = localImagePath;
    }

    public String getUrlImagePath() {
        return urlImagePath;
    }

    public void setUrlImagePath(String urlImagePath) {
        this.urlImagePath = urlImagePath;
    }

    public float getCompareScore() {
        return compareScore;
    }

    public void setCompareScore(float compareScore) {
        this.compareScore = compareScore;
    }

    public List<Float> getFeatures() {
        return features;
    }

    public void setFeatures(List<Float> features) {
        this.features = features;
    }

    public boolean isServerSync() {
        return serverSync;
    }

    public void setServerSync(boolean serverSync) {
        this.serverSync = serverSync;
    }

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", name=" + name + ", userId=" + userId + ", localImagePath=" + localImagePath
				+ ", urlImagePath=" + urlImagePath + ", compareScore=" + compareScore + ", features=" + features
				+ ", serverSync=" + serverSync + "]";
	}
    
    
    
}

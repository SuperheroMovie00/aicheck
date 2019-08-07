/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.form;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 1:28 PM 2019/1/21
 */

public class AdvertisingImagesForm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**图片*/
    @NotNull
    private String[] images;
    /**播放分组*/
    @NotNull
    private Integer groupId;

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}

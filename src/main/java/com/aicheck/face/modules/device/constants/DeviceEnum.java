/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.device.constants;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:09 PM 2019/1/29
 */
public enum DeviceEnum {

    MOBILE("mobile"),BOX("box");

    DeviceEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}

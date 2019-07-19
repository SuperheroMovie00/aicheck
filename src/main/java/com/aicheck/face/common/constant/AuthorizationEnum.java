/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.common.constant;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:42 PM 2019/1/25
 */
public enum AuthorizationEnum {
    TOKEN("297e325a66f62bae0166f631b0b20002"),EXPIRE_TIME(String.valueOf( (5 * 60 * 1000) ));

    private String value;

    AuthorizationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

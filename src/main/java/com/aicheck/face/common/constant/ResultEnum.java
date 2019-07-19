/*
 * Copyright  2018 Yiyuan Networks 上海义援网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.common.constant;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:45 PM 2018/11/21
 */
public enum ResultEnum {
    SUCCESS(200, "success"),SYSTEM_ERROR(-1, "系统错误"),IS_NOT_EXIST(-2,"数据不存在"),FACE_EXIST(10001,"人脸已存在"),MOBILE_REGISTER(10002,"手机号已注册"),MOBILE_FORMAT_NOT(10003,"手机格式不正确"),
    FACE_QUALITY_IS_BAD(10002,"人脸质量差"),PATH_NOT_EMPTY(20001,"path不能为空");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultEnum getResultEnum(Integer code) {

        for (ResultEnum resultEnum : ResultEnum.values()) {
            if (resultEnum.getCode().equals(code))
                return resultEnum;
        }

        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}

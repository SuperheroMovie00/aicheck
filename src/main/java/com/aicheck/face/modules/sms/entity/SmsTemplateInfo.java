/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.sms.entity;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 4:42 PM 2018/12/4
 */
public class SmsTemplateInfo {

    @NotNull
    private Integer code;
    @NotNull
    @Size(min=1)
    private List<String> receivers;
    @NotEmpty
    private String oneStr;
    @NotEmpty
    private String twoStr;
    @NotEmpty
    private String threeStr;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public String getOneStr() {
        return oneStr;
    }

    public void setOneStr(String oneStr) {
        this.oneStr = oneStr;
    }

    public String getTwoStr() {
        return twoStr;
    }

    public void setTwoStr(String twoStr) {
        this.twoStr = twoStr;
    }

    public String getThreeStr() {
        return threeStr;
    }

    public void setThreeStr(String threeStr) {
        this.threeStr = threeStr;
    }
}

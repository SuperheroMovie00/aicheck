/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.exceptionRecord.entity;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:22 PM 2019/1/24
 */
public class ExceptionRecordForm {

    /**
     * 客户id
     */
    private Integer customerId;
    /**
     * 异常信息
     */
    private String exceptionMessage;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

}

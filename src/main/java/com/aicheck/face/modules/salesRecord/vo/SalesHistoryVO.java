/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.salesRecord.vo;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 8:33 PM 2018/11/26
 */
public class SalesHistoryVO {

    private String customerName;

    private String customerId;

    private List<SalesRecordVO> salesRecordVOS;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<SalesRecordVO> getSalesRecordVOS() {
        return salesRecordVOS;
    }

    public void setSalesRecordVOS(List<SalesRecordVO> salesRecordVOS) {
        this.salesRecordVOS = salesRecordVOS;
    }
}

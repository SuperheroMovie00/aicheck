/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.salesRecord.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:15 PM 2018/11/22
 */
public class SalesRecordForm {

    /**
     * 客户id
     */
    @NotNull(message = "customerId 不能为空")
    private Integer customerId;

    @NotEmpty(message = "salesProductFormList 不能为空")
    private List<SalesProductForm> salesProductFormList;


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<SalesProductForm> getSalesProductFormList() {
        return salesProductFormList;
    }

    public void setSalesProductFormList(List<SalesProductForm> salesProductFormList) {
        this.salesProductFormList = salesProductFormList;
    }
}

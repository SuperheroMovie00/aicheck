/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.salesRecord.entity;

import javax.validation.constraints.NotNull;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:43 PM 2018/11/25
 */
public class SalesProductForm {
    @NotNull
    private Integer productId;
    @NotNull
    private Integer quantity;

    /**
     * 备注
     */
    private String remark;


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

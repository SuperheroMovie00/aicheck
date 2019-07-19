/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.salesDetail.vo;

import com.aicheck.face.modules.product.entity.Product;

import java.util.Date;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:57 PM 2018/11/25
 */
public class SalesDetailVO {

    /**
     * 商品id
     */
    private Product product;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建者id
     */
    private Integer createId;
    /**
     * 创建时间
     */
    private Date createTime;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

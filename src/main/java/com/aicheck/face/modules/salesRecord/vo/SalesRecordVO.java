/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.salesRecord.vo;

import com.aicheck.face.modules.customer.vo.CustomerVO;
import com.aicheck.face.modules.salesDetail.vo.SalesDetailVO;

import java.util.Date;
import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 4:05 PM 2018/11/22
 */
public class SalesRecordVO {

    /**
     * 客户
     */
    private CustomerVO customerVO;
    /**
     * 购买详细
     */
    private List<SalesDetailVO> salesDetailVOS;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人
     */
    private Integer createId;
    /**
     * 创建时间
     */
    private Date createTime;

    public List<SalesDetailVO> getSalesDetailVOS() {
        return salesDetailVOS;
    }

    public void setSalesDetailVOS(List<SalesDetailVO> salesDetailVOS) {
        this.salesDetailVOS = salesDetailVOS;
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

    public CustomerVO getCustomerVO() {
        return customerVO;
    }

    public void setCustomerVO(CustomerVO customerVO) {
        this.customerVO = customerVO;
    }
}

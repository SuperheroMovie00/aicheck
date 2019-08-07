/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.identifyRecord.vo;

import com.aicheck.face.modules.customer.vo.CustomerVO;

import java.util.Date;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:39 PM 2018/11/23
 */
public class IdentifyRecordVO {

    /**
     * 客户
     */
    private CustomerVO customerVO;
    /**
     * 时间
     */
    private Date createTime;

    public CustomerVO getCustomerVO() {
        return customerVO;
    }

    public void setCustomerVO(CustomerVO customerVO) {
        this.customerVO = customerVO;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

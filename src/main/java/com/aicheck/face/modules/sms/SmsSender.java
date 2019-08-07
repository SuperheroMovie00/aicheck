/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.sms;

import com.aicheck.face.modules.sms.entity.SmsTemplateInfo;
import com.aicheck.face.modules.sms.exception.SmsException;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:17 PM 2018/12/3
 */
public interface SmsSender {

    String sender(SmsTemplateInfo smsTemplateInfo) throws SmsException;

    String sender(String mobile,String code);

}

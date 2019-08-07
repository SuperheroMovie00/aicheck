/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.sms.constant;

/**
 * @Author: liaojin
 * @Description: 短信模版 枚举类
 * @Date: Created in 下午3:19 2018/10/22
 */
public enum SmsEnum {
    NOTIFY_ACTIVITY(397304,"本店定于{}月{}号进行会员促销活动，欢迎莅临。地址：{}退订回N"),NOTIFY_PREFERENTIAL(397306,"为你送上{}张{}元立减券，限时{}天，请查看公众号会员优惠。退订回N"),
    VERIFICATION_CODE(321051,"验证码模版");

    SmsEnum(Integer code, String template) {
        this.code = code;
        this.template = template;
    }

    private Integer code;
    private String template;

    public Integer getCode() {
        return code;
    }

    public String getTemplate() {
        return template;
    }

    public static SmsEnum getSmsEnum(int code) {

        for (SmsEnum smsEnum : SmsEnum.values()) {
            if (smsEnum.getCode().equals(code))
                return smsEnum;
        }

        return null;
    }


}

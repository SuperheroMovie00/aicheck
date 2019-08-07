/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.nettyPush;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:28 PM 2019/1/23
 */
public enum MessageTypeEnum {

    PULL("pull",1),SAVE("save",2),UPDATE("update",3),DELETE("delete",4),NEW_CUSTOMER("new",5),OLD_CUSTOMER("old",6),CHECK_FACE("checkface",7);


    MessageTypeEnum(String value, Integer code) {
        this.value = value;
        this.code = code;
    }

    public static MessageTypeEnum getMessageTypeEnum(Integer code) {

        for (MessageTypeEnum messageTypeEnum : MessageTypeEnum.values()) {
            if (messageTypeEnum.getCode().equals(code))
                return messageTypeEnum;
        }

        return null;
    }

    public static MessageTypeEnum getMessageTypeEnum(String type) {

        for (MessageTypeEnum messageTypeEnum : MessageTypeEnum.values()) {
            if (messageTypeEnum.getValue().equals(type))
                return messageTypeEnum;
        }

        return null;
    }

    private String value;

    private Integer code;

    public String getValue() {
        return value;
    }

    public Integer getCode() {
        return code;
    }
}

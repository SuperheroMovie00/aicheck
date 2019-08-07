/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.sms.exception;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 下午5:24 2018/10/22
 */
public class SmsException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code = 400;

    private String msg;

    public SmsException(String msg) {
        super(msg);
        this.msg = msg;
    }
    public SmsException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public SmsException(int code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public SmsException(int code, String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

/*
 * Copyright  2018 Yiyuan Networks 上海义援网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.common.exception;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:51 PM 2019/1/22
 */
public class FaceException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
    private int code = 500;

    public FaceException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public FaceException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public FaceException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public FaceException(String msg, int code, Throwable e) {
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

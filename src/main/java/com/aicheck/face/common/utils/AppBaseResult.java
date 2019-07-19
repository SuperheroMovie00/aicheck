/*
 * Copyright  2018 Yiyuan Networks 上海义援网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.common.utils;


import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @Author: liaojin
 * @Description:
 * @Date: 4:02 PM 2019/1/22
 */
public class AppBaseResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code = 500;
	private String message = "";
	private String data = "";
	private String version = "1.0";
	private String mobile = "";

	public final static int SUCCESS = 200;
	public final static int FAIL = 500;


	@SuppressWarnings("rawtypes")
	public static AppBaseResult success(String msg){
		AppBaseResult appBaseResult = new AppBaseResult();
		appBaseResult.setCode(SUCCESS);
		appBaseResult.setMessage(msg);
		return appBaseResult;
	}

	@SuppressWarnings("rawtypes")
	public static AppBaseResult success(){
		AppBaseResult appBaseResult = new AppBaseResult();
		appBaseResult.setCode(SUCCESS);
		appBaseResult.setMessage("请求成功");
		return appBaseResult;
	}

	@SuppressWarnings("rawtypes")
	public static AppBaseResult error(String msg){
		AppBaseResult appBaseResult = new AppBaseResult();
		appBaseResult.setCode(FAIL);
		appBaseResult.setMessage(msg);
		return appBaseResult;
	}

	@SuppressWarnings("rawtypes")
	public static AppBaseResult error(int code,String msg){
		AppBaseResult appBaseResult = new AppBaseResult();
		appBaseResult.setCode(code);
		appBaseResult.setMessage(msg);
		return appBaseResult;
	}

	@SuppressWarnings("rawtypes")
	public static AppBaseResult error() {
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
	}

	
	public int getCode() {
		return code;
	}
	@SuppressWarnings("rawtypes")
	public AppBaseResult setCode(int status) {
		this.code = status;
		return this;
	}
	public String getMessage() {
		return message;
	}
	@SuppressWarnings("rawtypes")
	public AppBaseResult setMessage(String message) {
		this.message = message;
		return this;
	}
	public String getData() {
		return  this.data;
	}

	public void setData(String data) {
		this.data = data;
	}


	public String getVersion() {
		return version;
	}
	public AppBaseResult<T> setVersion(String version) {
		this.version = version;
		return this;
	}

	public String getMobile() {
		return mobile;
	}

	@SuppressWarnings("rawtypes")
	public AppBaseResult setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}

	@Override
	public String toString() {
		return "{" +
				"code='" + code + '\'' +
				", msg='" + message + '\'' +
				", data='" + data + '\'' +
				", version='" + version + '\'' +
				", mobile='" + mobile + '\'' +
				'}';
	}
}

/*
 * Copyright  2018 LianChao Networks 上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.vo;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liaojin
 * @Description:
 * @Date: 4:00 PM 2019/1/22
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", 0);
		put("data", 0);
		put("msg", "success");
	}
	
	public static R error() {
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("data", msg);
		r.put("msg", "success");
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.put("data",map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	public static R ok(Object value){
		R r = new R();
		r.put("data",value);
		return r;
	}

	public R put(String key, Object value) {
		//大家快来幅度萨芬
		super.put(key, value);
		return this;
	}
}

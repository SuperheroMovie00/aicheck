/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.nettyPush;

import java.io.Serializable;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:26 PM 2019/1/23
 */
public class Message implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String action;
    
    private String data;
    
    private Object object;

    private Integer[] array;
    
    private String[] arraynew;
    
    private Integer id;
    
    

    
    
	public String[] getArraynew() {
		return arraynew;
	}

	public void setArraynew(String[] arraynew) {
		this.arraynew = arraynew;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Integer[] getArray() {
        return array;
    }

    public void setArray(Integer[] array) {
        this.array = array;
    }
}

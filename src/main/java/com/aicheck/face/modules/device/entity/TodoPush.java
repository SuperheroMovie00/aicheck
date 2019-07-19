/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.device.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:43 PM 2019/1/25
 */
@Entity
@Table(name = "todo_push")
public class TodoPush implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String deviceId;

    private Integer type;

    private String message;
    @CreationTimestamp
    private Date createTime;

    private Integer status;

    private Integer dataId;
    
    private String result;
    
    private Date result_time;
    private Date send_time;


    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public Date getResult_time() {
		return result_time;
	}

	public void setResult_time(Date result_time) {
		this.result_time = result_time;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	@Override
	public String toString() {
		return "TodoPush [id=" + id + ", deviceId=" + deviceId + ", type=" + type + ", message=" + message
				+ ", createTime=" + createTime + ", status=" + status + ", dataId=" + dataId + ", result=" + result
				+ "]";
	}


    
    
}

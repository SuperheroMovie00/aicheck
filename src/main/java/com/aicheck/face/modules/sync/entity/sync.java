package com.aicheck.face.modules.sync.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "sync")
public class sync {
	/**
     * 主键
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String func;
	private int dataId;	
	private String receiver;
	private int status;
	
	@CreationTimestamp
	private Date createTime;
	private Date sucessTime;
	private Date failedTime;
	private String failedMsg;
	private int failedCount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFunc() {
		return func;
	}
	public void setFunc(String func) {
		this.func = func;
	}
	public int getDataId() {
		return dataId;
	}
	public void setDataId(int dataId) {
		this.dataId = dataId;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getSucessTime() {
		return sucessTime;
	}
	public void setSucessTime(Date sucessTime) {
		this.sucessTime = sucessTime;
	}
	public Date getFailedTime() {
		return failedTime;
	}
	public void setFailedTime(Date failedTime) {
		this.failedTime = failedTime;
	}
	public String getFailedMsg() {
		return failedMsg;
	}
	public void setFailedMsg(String failedMsg) {
		this.failedMsg = failedMsg;
	}
	public int getFailedCount() {
		return failedCount;
	}
	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}
	@Override
	public String toString() {
		return "sync [id=" + id + ", func=" + func + ", dataId=" + dataId + ", receiver=" + receiver + ", status="
				+ status + ", createTime=" + createTime + ", sucessTime=" + sucessTime + ", failedTime=" + failedTime
				+ ", failedMsg=" + failedMsg + ", failedCount=" + failedCount + "]";
	}

	
}

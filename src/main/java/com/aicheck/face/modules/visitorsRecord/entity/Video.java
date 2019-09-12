package com.aicheck.face.modules.visitorsRecord.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "video")
public class Video {
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private String dataType;
	private String data;
	private Date createTime;
	private Date modificationTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModificationTime() {
		return modificationTime;
	}
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}
	@Override
	public String toString() {
		return "Video [id=" + id + ", dataType=" + dataType + ", data=" + data + ", createTime=" + createTime
				+ ", modificationTime=" + modificationTime + "]";
	}
	
	
	
	

}

package com.aicheck.face.modules.visitorsRecord.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "carstatistics")
public class Carstatistics implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private String carnumber;        	//车牌
	private Integer status;
	private Integer schedule;			//处理进度
	private Date createTime;			//创建时间
	private Date modificationTime;      //修改时间
	
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
	public String getCarnumber() {
		return carnumber;
	}
	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}
	public Integer getSchedule() {
		return schedule;
	}
	public void setSchedule(Integer schedule) {
		this.schedule = schedule;
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
		return "Carstatistics [id=" + id + ", carnumber=" + carnumber + ", status=" + status + ", schedule=" + schedule
				+ ", createTime=" + createTime + ", modificationTime=" + modificationTime + "]";
	}
	

	
	
}

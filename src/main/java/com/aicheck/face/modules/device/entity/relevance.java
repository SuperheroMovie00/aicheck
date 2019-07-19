package com.aicheck.face.modules.device.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "relevance")
public class relevance {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer aideviceId;
	private Integer boxdeviceId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAideviceId() {
		return aideviceId;
	}
	public void setAideviceId(Integer aideviceId) {
		this.aideviceId = aideviceId;
	}
	public Integer getBoxdeviceId() {
		return boxdeviceId;
	}
	public void setBoxdeviceId(Integer boxdeviceId) {
		this.boxdeviceId = boxdeviceId;
	}

}

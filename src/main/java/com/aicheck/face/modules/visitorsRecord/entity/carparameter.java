package com.aicheck.face.modules.visitorsRecord.entity;

public class carparameter {
	
	private String carnumber;
	private Integer schedule;
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
	@Override
	public String toString() {
		return "carparameter [carnumber=" + carnumber + ", schedule=" + schedule + "]";
	}
	
	
	

}

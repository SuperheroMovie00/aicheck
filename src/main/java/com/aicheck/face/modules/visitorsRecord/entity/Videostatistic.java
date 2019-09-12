package com.aicheck.face.modules.visitorsRecord.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "videostatistic")
public class Videostatistic implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String dateType;
    private String dateInformation;
    private Date createTime;
    private Date modificationTime;
    

    
    
  
	public Integer getId() {
        return id;
    }

    public String getDateType() {
        return dateType;
    }

    public String getDateInformation() {
        return dateInformation;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public void setDateInformation(String dateInformation) {
        this.dateInformation = dateInformation;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }
}

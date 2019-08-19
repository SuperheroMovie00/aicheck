package com.aicheck.face.modules.system.entity;


import javax.persistence.*;

@Entity
@Table(name = "systems")
public class SystemParameter {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String systemType;
    private String systemName;
    private String systemValue;
    private Integer status;
    private String remark;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public void setSystemValue(String systemValue) {
        this.systemValue = systemValue;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public String getSystemType() {
        return systemType;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getSystemValue() {
        return systemValue;
    }

    public Integer getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }
}

package com.aicheck.face.modules.pathseting.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pathseting")
public class pathseting {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String type;

    private  String path;

    private Date createTime;

    private  Date modificationTime;


    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
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

    public void setType(String type) {
        this.type = type;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }


}

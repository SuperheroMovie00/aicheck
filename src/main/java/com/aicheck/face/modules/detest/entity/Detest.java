package com.aicheck.face.modules.detest.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "detest")
public class Detest {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private  String name;
    private  Integer age;
    private  String phone;
    private  String email;
    private String status;
    private Date createTime;
    private Date modificationTime;


    public void setStatus(Integer id) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getModificationTime() {
        return modificationTime;
    }
}

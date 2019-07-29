package com.aicheck.face.modules.pathseting.entity;

public class pathsetinginput {

    private String visitorspath;
    private String customerpath;
    private String aipath;
    private String deletevisitorytime;


    public String getDeletevisitorytime() {
        return deletevisitorytime;
    }
    public void setDeletevisitorytime(String deletevisitorytime) {
        this.deletevisitorytime = deletevisitorytime;
    }

    public String getVisitorspath() {
        return visitorspath;
    }

    public String getCustomerpath() {
        return customerpath;
    }

    public String getAipath() {
        return aipath;
    }

    public void setVisitorspath(String visitorspath) {
        this.visitorspath = visitorspath;
    }

    public void setCustomerpath(String customerpath) {
        this.customerpath = customerpath;
    }

    public void setAipath(String aipath) {
        this.aipath = aipath;
    }


}

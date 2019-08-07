/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.tags.entity;


/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 4:00 PM 2018/11/26
 */
public class TagsForm {

    /**
     * 名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}

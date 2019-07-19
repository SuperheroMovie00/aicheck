package com.aicheck.face.modules.levels.entity;

import javax.validation.constraints.NotEmpty;

public class LevelsForm {
	@NotEmpty
	private String name;
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
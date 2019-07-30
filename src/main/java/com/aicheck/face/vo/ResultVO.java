/*
 * Copyright  2018 LianChao Networks 上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.vo;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:43 PM 2018/11/21
 */
public class ResultVO<T> {
	

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String message;

    /** 具体的内容. */
    private T data;
    /** 数据总数量 */
    private Long count;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}

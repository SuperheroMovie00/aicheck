/*
 * Copyright  2018 LianChao Networks 上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.common.utils;

import com.aicheck.face.common.constant.ResultEnum;
import com.aicheck.face.vo.ResultVO;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:44 PM 2018/11/21
 */
public class ResultUtils {

    /**
     * 操作成功返回的消息
     * @param object
     * @return
     */
    public static ResultVO<Object> success(Object object) {
        ResultVO<Object> result = new ResultVO<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }
    /**
     * 操作成功返回的消息 对success的重载
     *
     * @return
     */
    public static ResultVO<Object> success() {
        ResultVO<Object> result = new ResultVO<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getMsg());
        return result;
    }
    /**
     * 操作成功返回的消息 对success的重载
     *
     * @return
     */
    public static ResultVO<Object> success(Object object,Long count) {
        ResultVO<Object> result = new ResultVO<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        result.setCount(count);
        return result;
    }

    /**
     * 操作失败返回的消息
     * @param code
     * @param msg
     * @return
     */
    public static ResultVO<Object> error(int code,String msg) {
        ResultVO<Object> result = new ResultVO<>();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }
    /**
     * 操作失败返回消息，对error的重载
     * @param resultEnum
     * @return
     */
    public static ResultVO<Object> error(ResultEnum resultEnum) {
        ResultVO<Object> result = new ResultVO<>();
        result.setCode(resultEnum.getCode());
        result.setMessage(resultEnum.getMsg());
        return result;
    }

    /**
     * 操作失败返回消息，对error的重载
     *
     * @return
     */
    public static ResultVO<Object> error() {
        ResultVO<Object> result = new ResultVO<>();
        result.setCode(ResultEnum.SYSTEM_ERROR.getCode());
        result.setMessage(ResultEnum.SYSTEM_ERROR.getMsg());
        return result;
    }
}
